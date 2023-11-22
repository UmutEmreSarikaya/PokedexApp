package com.umut.pokedexapp.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.umut.pokedexapp.R
import com.umut.pokedexapp.databinding.FragmentPokemonListBinding
import com.umut.pokedexapp.domain.model.Pokemon
import com.umut.pokedexapp.presentation.viewmodel.PokemonListViewModel
import com.umut.pokedexapp.util.viewGone
import com.umut.pokedexapp.util.viewVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment() {
    private val pokemonListViewModel: PokemonListViewModel by viewModels()
    private lateinit var binding: FragmentPokemonListBinding
    private val pokemonListAdapter = PokemonListAdapter()
    private var unfilteredPokemonList = listOf<Pokemon>()
    private var filteredPokemonList = listOf<Pokemon>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

        binding.rvPokemon.adapter = pokemonListAdapter.apply {
            itemClickListener = { pokemon ->
                val action =
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(
                        name = pokemon.name
                    )
                findNavController().navigate(action)
            }
        }
        initObservers()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    pokemonListAdapter.submitList(unfilteredPokemonList)
                } else {
                    filteredPokemonList =
                        unfilteredPokemonList.filter { pokemon -> pokemon.name.contains(newText.toString()) }
                    pokemonListAdapter.submitList(filteredPokemonList)
                }
                return true
            }
        })
    }

    private fun initObservers() {
        pokemonListViewModel.pokemonListResource.observe(viewLifecycleOwner) { pokemonListResource ->
            binding.apply {
                rvPokemon.viewVisible()
                tvError.viewGone()
            }
            pokemonListResource.data?.let { unfilteredPokemonList = pokemonListResource.data }
            pokemonListAdapter.submitList(unfilteredPokemonList)
        }

        pokemonListViewModel.pokemonListLoading.observe(viewLifecycleOwner) { loadingResource ->
            loadingResource.data?.let { loading ->
                if (loading) {
                    binding.apply {
                        Log.d("myLog", "true")
                        progressBar.viewVisible()
                        rvPokemon.viewGone()
                        tvError.viewGone()
                    }
                } else {
                    Log.d("myLog", "false")
                    binding.progressBar.viewGone()
                }
            }
        }

        pokemonListViewModel.pokemonListError.observe(viewLifecycleOwner) {errorResource ->
            errorResource.data?.let { error ->
                if (error) {
                    binding.apply {
                        tvError.viewVisible()
                        rvPokemon.viewGone()
                    }
                } else {
                    binding.tvError.viewGone()
                }
            }

        }
    }
}