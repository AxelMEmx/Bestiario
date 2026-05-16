package com.bestiario.hp.ui.juego

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bestiario.hp.data.Bestia
import com.bestiario.hp.databinding.ItemIntentoBinding

data class ResultadoIntento(
    val bestiaIntentada: Bestia,
    val habitatCorrecto: Boolean,
    val peligroCorrecto: Boolean,
    val peligroDiferencia: Int,   // positivo = secreta es mayor, negativo = secreta es menor
    val aparicionCorrecta: Boolean,
    val tipoCorrecto: Boolean
)

class IntentoAdapter : RecyclerView.Adapter<IntentoAdapter.IntentoViewHolder>() {

    private val intentos = mutableListOf<ResultadoIntento>()

    fun agregarIntento(resultado: ResultadoIntento) {
        intentos.add(0, resultado)
        notifyItemInserted(0)
    }

    fun limpiar() {
        intentos.clear()
        notifyDataSetChanged()
    }

    inner class IntentoViewHolder(private val binding: ItemIntentoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultado: ResultadoIntento) {
            binding.textNombreIntento.text = resultado.bestiaIntentada.nombre

            // Habitat
            binding.celdaHabitat.text = resultado.bestiaIntentada.habitat.take(20)
            binding.celdaHabitat.setBackgroundColor(
                if (resultado.habitatCorrecto) VERDE else ROJO
            )

            // Peligro con flechas
            binding.celdaPeligro.setBackgroundColor(
                when {
                    resultado.peligroCorrecto -> VERDE
                    Math.abs(resultado.peligroDiferencia) == 1 -> AMARILLO
                    else -> ROJO
                }
            )
            binding.celdaPeligro.text = when {
                resultado.peligroCorrecto -> "${resultado.bestiaIntentada.nivelPeligro}"
                resultado.peligroDiferencia > 0 -> "${resultado.bestiaIntentada.nivelPeligro} ↑"
                else -> "${resultado.bestiaIntentada.nivelPeligro} ↓"
            }

            // Aparicion
            binding.celdaAparicion.text = resultado.bestiaIntentada.primeraAparicion.take(20)
            binding.celdaAparicion.setBackgroundColor(
                if (resultado.aparicionCorrecta) VERDE else ROJO
            )

            // Tipo (habitat general)
            binding.celdaTipo.text = obtenerTipo(resultado.bestiaIntentada.habitat)
            binding.celdaTipo.setBackgroundColor(
                if (resultado.tipoCorrecto) VERDE else ROJO
            )
        }

        private fun obtenerTipo(habitat: String): String {
            return when {
                habitat.contains("mar", ignoreCase = true) ||
                        habitat.contains("océano", ignoreCase = true) ||
                        habitat.contains("lago", ignoreCase = true) -> "Acuatico"
                habitat.contains("bosque", ignoreCase = true) ||
                        habitat.contains("selva", ignoreCase = true) -> "Bosque"
                habitat.contains("monta", ignoreCase = true) -> "Montana"
                habitat.contains("africa", ignoreCase = true) -> "Africa"
                habitat.contains("europa", ignoreCase = true) ||
                        habitat.contains("bretana", ignoreCase = true) ||
                        habitat.contains("reino unido", ignoreCase = true) -> "Europa"
                habitat.contains("america", ignoreCase = true) ||
                        habitat.contains("EE.UU", ignoreCase = true) -> "America"
                habitat.contains("asia", ignoreCase = true) ||
                        habitat.contains("china", ignoreCase = true) ||
                        habitat.contains("japon", ignoreCase = true) -> "Asia"
                else -> "Global"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentoViewHolder {
        val binding = ItemIntentoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntentoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntentoViewHolder, position: Int) {
        holder.bind(intentos[position])
    }

    override fun getItemCount() = intentos.size

    companion object {
        val VERDE = Color.parseColor("#2D6A4F")
        val ROJO = Color.parseColor("#8B0000")
        val AMARILLO = Color.parseColor("#B5860D")
    }
}