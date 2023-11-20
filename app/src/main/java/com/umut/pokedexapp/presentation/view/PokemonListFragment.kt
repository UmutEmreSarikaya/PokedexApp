package com.umut.pokedexapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.umut.pokedexapp.R
import com.umut.pokedexapp.databinding.FragmentPokemonListBinding
import com.umut.pokedexapp.presentation.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment() {
    private val pokemonListViewModel: PokemonListViewModel by viewModels()
    private lateinit var binding: FragmentPokemonListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    private fun initObservers() {
        pokemonListViewModel.pokemonListResource.observe(viewLifecycleOwner) {

        }

        pokemonListViewModel.pokemonListLoading.observe(viewLifecycleOwner) {

        }

        pokemonListViewModel.pokemonListError.observe(viewLifecycleOwner) {

        }
    }
}