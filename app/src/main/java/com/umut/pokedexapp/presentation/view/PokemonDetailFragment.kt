package com.umut.pokedexapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.umut.pokedexapp.databinding.FragmentPokemonDetailBinding
import com.umut.pokedexapp.presentation.viewmodel.PokemonDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private val pokemonDetailViewModel : PokemonDetailViewModel by viewModels()
    private lateinit var binding: FragmentPokemonDetailBinding
    private val safeArgs : PokemonDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).updateToolbarView("PokemonDetailFragment")
        binding.pokemonName.text = safeArgs.name

    }
}