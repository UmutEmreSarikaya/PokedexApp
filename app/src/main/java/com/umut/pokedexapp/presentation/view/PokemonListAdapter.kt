package com.umut.pokedexapp.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umut.pokedexapp.databinding.ItemPokemonBinding
import com.umut.pokedexapp.domain.model.Pokemon

class PokemonListAdapter : ListAdapter<Pokemon, PokemonListAdapter.PokemonListViewHolder>(
    CharacterDiffCallback
) {
    private lateinit var binding: ItemPokemonBinding
    var itemClickListener: ((Pokemon) -> Unit)? = null

    class PokemonListViewHolder(
        private val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            pokemon: Pokemon,
            itemClickListener: ((Pokemon) -> Unit)?
        ) {
            binding.apply {
                tvPokemonId.text = "0003"
                itemLayout.setOnClickListener {
                    itemClickListener?.invoke(pokemon)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }
}

object CharacterDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name && oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name && oldItem.url == newItem.url
    }
}