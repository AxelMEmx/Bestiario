package com.bestiario.hp.ui.juego

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bestiario.hp.data.Bestia
import com.bestiario.hp.data.BestiaViewModel
import com.bestiario.hp.databinding.FragmentJuegoBinding

class JuegoFragment : Fragment() {

    private var _binding: FragmentJuegoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BestiaViewModel by activityViewModels()

    private lateinit var intentoAdapter: IntentoAdapter
    private var bestiaSecreta: Bestia? = null
    private var todasLasBestias: List<Bestia> = emptyList()
    private var intentosUsados = 0
    private val maxIntentos = 8
    private var juegoTerminado = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJuegoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intentoAdapter = IntentoAdapter()
        binding.recyclerIntentos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerIntentos.adapter = intentoAdapter

        viewModel.todasLasBestias.observe(viewLifecycleOwner) { lista ->
            if (lista.isNotEmpty()) {
                todasLasBestias = lista
                if (bestiaSecreta == null) iniciarJuego(lista)

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    lista.map { it.nombre }
                )
                binding.autoCompleteBestia.setAdapter(adapter)
                binding.autoCompleteBestia.threshold = 1
            }
        }

        binding.btnAdivinar.setOnClickListener { procesarIntento() }
    }

    private fun iniciarJuego(lista: List<Bestia>) {
        bestiaSecreta = lista.random()
        intentosUsados = 0
        juegoTerminado = false
        actualizarContador()
    }

    private fun procesarIntento() {
        if (juegoTerminado) return

        val nombreEscrito = binding.autoCompleteBestia.text.toString().trim()
        if (nombreEscrito.isEmpty()) return

        val bestiaIntentada = todasLasBestias.find {
            it.nombre.equals(nombreEscrito, ignoreCase = true)
        }

        if (bestiaIntentada == null) {
            binding.autoCompleteBestia.error = "Criatura no encontrada en el bestiario"
            return
        }

        val secreta = bestiaSecreta ?: return
        val diferenciaPeligro = secreta.nivelPeligro - bestiaIntentada.nivelPeligro

        val resultado = ResultadoIntento(
            bestiaIntentada = bestiaIntentada,
            habitatCorrecto = bestiaIntentada.habitat == secreta.habitat,
            peligroCorrecto = bestiaIntentada.nivelPeligro == secreta.nivelPeligro,
            peligroDiferencia = diferenciaPeligro,
            aparicionCorrecta = bestiaIntentada.primeraAparicion == secreta.primeraAparicion,
            tipoCorrecto = obtenerRegion(bestiaIntentada.habitat) == obtenerRegion(secreta.habitat)
        )

        intentoAdapter.agregarIntento(resultado)
        binding.autoCompleteBestia.text.clear()
        intentosUsados++
        actualizarContador()

        when {
            bestiaIntentada.id == secreta.id -> {
                juegoTerminado = true
                mostrarResultado(gano = true)
            }
            intentosUsados >= maxIntentos -> {
                juegoTerminado = true
                mostrarResultado(gano = false)
            }
        }
    }

    private fun obtenerRegion(habitat: String): String {
        return when {
            habitat.contains("mar", ignoreCase = true) ||
                    habitat.contains("océano", ignoreCase = true) -> "Acuatico"
            habitat.contains("bosque", ignoreCase = true) ||
                    habitat.contains("selva", ignoreCase = true) -> "Bosque"
            habitat.contains("africa", ignoreCase = true) -> "Africa"
            habitat.contains("europa", ignoreCase = true) ||
                    habitat.contains("bretana", ignoreCase = true) -> "Europa"
            habitat.contains("america", ignoreCase = true) ||
                    habitat.contains("EE.UU", ignoreCase = true) -> "America"
            habitat.contains("china", ignoreCase = true) ||
                    habitat.contains("japon", ignoreCase = true) -> "Asia"
            else -> "Global"
        }
    }

    private fun actualizarContador() {
        binding.textIntentos.text = "Intentos: $intentosUsados / $maxIntentos"
    }

    private fun mostrarResultado(gano: Boolean) {
        val titulo = if (gano) "Correcto!" else "Game Over"
        val mensaje = if (gano)
            "Adivinaste a ${bestiaSecreta?.nombre} en $intentosUsados intentos"
        else
            "La bestia secreta era: ${bestiaSecreta?.nombre}"

        AlertDialog.Builder(requireContext())
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Nueva partida") { _, _ ->
                intentoAdapter.limpiar()
                iniciarJuego(todasLasBestias)
            }
            .setCancelable(false)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}