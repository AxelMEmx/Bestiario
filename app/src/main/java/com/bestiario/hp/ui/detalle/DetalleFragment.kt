package com.bestiario.hp.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bestiario.hp.data.BestiaViewModel
import com.bestiario.hp.databinding.FragmentDetalleBinding

class DetalleFragment : Fragment() {

    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BestiaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bestiaId = arguments?.getInt("bestiaId") ?: return

        viewModel.obtenerBestiaPorId(bestiaId).observe(viewLifecycleOwner) { bestia ->
            bestia?.let {
                binding.textNombre.text = it.nombre
                binding.textNombreLatin.text = it.nombreLatin
                binding.textDescripcion.text = it.descripcion
                binding.textHabitat.text = "  ${it.habitat}"
                binding.textHabilidades.text = "  ${it.habilidades}"
                binding.textAparicion.text = "  ${it.primeraAparicion}"

                val estrellas = "★".repeat(it.nivelPeligro) + "☆".repeat(5 - it.nivelPeligro)
                binding.textNivelPeligro.text = "  M.O.M.  $estrellas  "

                val textoFav = if (it.esFavorito) "★ En Favoritos" else "☆ Añadir a Favoritos"
                binding.btnFavorito.text = textoFav

                binding.btnFavorito.setOnClickListener { _ ->
                    viewModel.toggleFavorito(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}