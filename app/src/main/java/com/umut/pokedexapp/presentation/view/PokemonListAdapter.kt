package com.umut.pokedexapp.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                val pokemonId = extractIdFromUrl(pokemon.url)
                tvPokemonId.text = getPokemonIdFormatted(pokemonId)
                Glide.with(itemView)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png")
                    .into(ivPokemon)
                itemLayout.setOnClickListener {
                    itemClickListener?.invoke(pokemon)
                }
            }
        }

        private fun extractIdFromUrl(url: String): Int {
            val parts = url.split("/")
            return parts[parts.size - 2].toInt()
        }

        private fun getPokemonIdFormatted(id: Int): String {
            val formattedId = when (id) {
                in 1..9 -> "#000$id"
                in 10..99 -> "#00$id"
                in 100..999 -> "#0$id"
                else -> "#$id"
            }
            return formattedId
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