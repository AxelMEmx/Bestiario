package com.bestiario.hp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bestiario.hp.data.Bestia
import com.bestiario.hp.databinding.ItemBestiaBinding

class BestiaAdapter(
    private val onItemClick: (Bestia) -> Unit,
    private val onFavoritoClick: (Bestia) -> Unit
) : ListAdapter<Bestia, BestiaAdapter.BestiaViewHolder>(DiffCallback) {

    inner class BestiaViewHolder(private val binding: ItemBestiaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bestia: Bestia) {
            binding.textNombreItem.text = bestia.nombre
            binding.textLatinItem.text = bestia.nombreLatin
            binding.textPeligroItem.text = "M.O.M.: ${"★".repeat(bestia.nivelPeligro)}${"☆".repeat(5 - bestia.nivelPeligro)}"
            binding.textFavoritoItem.text = if (bestia.esFavorito) "★" else "☆"

            binding.root.setOnClickListener { onItemClick(bestia) }
            binding.textFavoritoItem.setOnClickListener { onFavoritoClick(bestia) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestiaViewHolder {
        val binding = ItemBestiaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BestiaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestiaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Bestia>() {
        override fun areItemsTheSame(oldItem: Bestia, newItem: Bestia) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Bestia, newItem: Bestia) = oldItem == newItem
    }
}