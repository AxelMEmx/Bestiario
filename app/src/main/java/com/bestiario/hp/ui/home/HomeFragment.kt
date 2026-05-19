package com.bestiario.hp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bestiario.hp.R
import com.bestiario.hp.data.BestiaViewModel
import com.bestiario.hp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BestiaViewModel by activityViewModels()
    private var ordenAscendente = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BestiaAdapter(
            onItemClick = { bestia ->
                val bundle = Bundle().apply { putInt("bestiaId", bestia.id) }
                findNavController().navigate(R.id.action_home_to_detalle, bundle)
            },
            onFavoritoClick = { bestia ->
                viewModel.toggleFavorito(bestia)
            }

        )
        binding.editBuscar.setOnEditorActionListener { _, _, _ ->
            binding.editBuscar.requestFocus()
            false
        }
        binding.recyclerBestias.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerBestias.adapter = adapter

        viewModel.todasLasBestias.observe(viewLifecycleOwner) { lista ->
            if (ordenAscendente && binding.editBuscar.text.isEmpty()) {
                adapter.submitList(lista)
            }
        }

        viewModel.todasDesc.observe(viewLifecycleOwner) { lista ->
            if (!ordenAscendente && binding.editBuscar.text.isEmpty()) {
                adapter.submitList(lista)
            }
        }

        viewModel.resultadosBusqueda.observe(viewLifecycleOwner) { lista ->
            if (binding.editBuscar.text.isNotEmpty()) {
                adapter.submitList(lista)
            }
        }

        binding.editBuscar.addTextChangedListener { texto ->
            if (texto.toString().isEmpty()) {
                if (ordenAscendente) {
                    viewModel.todasLasBestias.value?.let { adapter.submitList(it) }
                } else {
                    viewModel.todasDesc.value?.let { adapter.submitList(it) }
                }
            } else {
                viewModel.buscar(texto.toString())
            }
        }

        binding.btnOrdenar.setOnClickListener {
            ordenAscendente = !ordenAscendente
            binding.btnOrdenar.text = if (ordenAscendente) "A - Z" else "Z - A"
            if (ordenAscendente) {
                viewModel.todasLasBestias.value?.let { adapter.submitList(it) {
                    binding.recyclerBestias.scrollToPosition(0)
                }}
            } else {
                viewModel.todasDesc.value?.let { adapter.submitList(it) {
                    binding.recyclerBestias.scrollToPosition(0)
                }}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}