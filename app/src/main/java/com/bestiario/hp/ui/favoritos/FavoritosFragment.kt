package com.bestiario.hp.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bestiario.hp.R
import com.bestiario.hp.data.BestiaViewModel
import com.bestiario.hp.databinding.FragmentFavoritosBinding
import com.bestiario.hp.ui.home.BestiaAdapter

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BestiaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BestiaAdapter(
            onItemClick = { bestia ->
                val bundle = Bundle().apply { putInt("bestiaId", bestia.id) }
                findNavController().navigate(R.id.action_favoritos_to_detalle, bundle)
            },
            onFavoritoClick = { bestia ->
                viewModel.toggleFavorito(bestia)
            }
        )

        binding.recyclerFavoritos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavoritos.adapter = adapter

        viewModel.bestiasFavoritas.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
            binding.textVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}