package com.bestiario.hp.ui.detalle

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bestiario.hp.data.BestiaViewModel
import com.bestiario.hp.databinding.DialogImagenBinding
import com.bestiario.hp.databinding.FragmentDetalleBinding
import com.bumptech.glide.Glide

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

                Glide.with(this)
                    .load(
                        requireContext().resources.getIdentifier(
                            it.imagenUrl, "drawable", requireContext().packageName
                        )
                    )
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .centerCrop()
                    .into(binding.imageBestia)

                binding.textNombre.text = it.nombre
                binding.textNombreLatin.text = it.nombreLatin
                binding.textDescripcion.text = it.descripcion
                binding.textHabitat.text = it.habitat
                binding.textAparicion.text = it.primeraAparicion
                binding.textHabilidades.text = it.habilidades

                val estrellas = "★".repeat(it.nivelPeligro) + "☆".repeat(5 - it.nivelPeligro)
                binding.textNivelPeligro.text = "  M.O.M.  $estrellas  "

                val textoFav = if (it.esFavorito) "★ En Favoritos" else "☆ Añadir a Favoritos"
                binding.btnFavorito.text = textoFav

                binding.btnFavorito.setOnClickListener { _ ->
                    viewModel.toggleFavorito(it)
                }

                // Imagen expandible al tocar
                binding.imageBestia.setOnClickListener { _ ->
                    mostrarImagenExpandida(it.imagenUrl)
                }
            }
        }
    }

    private fun mostrarImagenExpandida(imagenUrl: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val dialogBinding = DialogImagenBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        Glide.with(this)
            .load(
                requireContext().resources.getIdentifier(
                    imagenUrl, "drawable", requireContext().packageName
                )
            )
            .into(dialogBinding.imagenExpandida)

        dialogBinding.btnCerrarImagen.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.imagenExpandida.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}