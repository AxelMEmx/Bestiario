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

        binding.recyclerBestias.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerBestias.adapter = adapter

        // Observar resultados de búsqueda
        viewModel.resultadosBusqueda.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }

        // Buscador en tiempo real
        binding.editBuscar.addTextChangedListener { texto ->
            viewModel.buscar(texto.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}