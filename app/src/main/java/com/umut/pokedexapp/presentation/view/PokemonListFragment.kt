package com.umut.pokedexapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umut.pokedexapp.R
import com.umut.pokedexapp.databinding.FragmentPokemonListBinding
import com.umut.pokedexapp.presentation.viewmodel.SharedViewModel
import com.umut.pokedexapp.util.extractIdFromUrl
import com.umut.pokedexapp.util.viewGone
import com.umut.pokedexapp.util.viewVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    private lateinit var binding: FragmentPokemonListBinding
    private val pokemonListAdapter = PokemonListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

        setAdapter()
        initObservers()
        setOnQueryTextListener()
        setOnClickListeners()
    }

    private fun setAdapter(){
        binding.rvPokemon.adapter = pokemonListAdapter.apply {
            itemClickListener = { pokemon ->
                sharedViewModel.pokemonName = pokemon.name
                findNavController().navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment)
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.pokemonListResource.collect { pokemonListResource ->
                binding.apply {
                    rvPokemon.viewVisible()
                    tvError.viewGone()
                }
                pokemonListResource.data?.let {
                    sharedViewModel.unfilteredPokemonList = pokemonListResource.data
                    sharedViewModel.filteredPokemonList = sharedViewModel.unfilteredPokemonList
                    pokemonListAdapter.submitList(sharedViewModel.unfilteredPokemonList)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.pokemonListLoading.collect { loadingResource ->
                loadingResource.data?.let { loading ->
                    if (loading) {
                        binding.apply {
                            progressBar.viewVisible()
                            rvPokemon.viewGone()
                            tvError.viewGone()
                        }
                    } else {
                        binding.progressBar.viewGone()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.pokemonListError.collect { errorResource ->
                errorResource.data?.let { error ->
                    if (error) {
                        binding.apply {
                            tvError.viewVisible()
                            rvPokemon.viewGone()
                            progressBar.viewGone()
                        }
                    } else {
                        binding.tvError.viewGone()
                    }
                }
            }
        }
    }

    private fun setOnQueryTextListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    sharedViewModel.filteredPokemonList = sharedViewModel.unfilteredPokemonList
                    pokemonListAdapter.submitList(sharedViewModel.unfilteredPokemonList)
                } else {
                        if (sharedViewModel.filterByNumber){
                            sharedViewModel.filteredPokemonList =  sharedViewModel.unfilteredPokemonList.filter { pokemon ->
                                pokemon.url.extractIdFromUrl().toString() == newText.toString()
                            }
                        } else {
                            sharedViewModel.filteredPokemonList =  sharedViewModel.unfilteredPokemonList.filter { pokemon ->
                                pokemon.name.contains(
                                    newText.toString()
                                )
                            }
                        }

                    pokemonListAdapter.submitList(sharedViewModel.filteredPokemonList)
                }
                return true
            }
        })
    }

    private fun setOnClickListeners(){
        binding.filterTypeButton.setOnClickListener {
            if (sharedViewModel.filterByNumber) {
                binding.filterTypeButton.setImageResource(R.drawable.baseline_format_color_text_24)
                sharedViewModel.filterByNumber = false
            } else {
                binding.filterTypeButton.setImageResource(R.drawable.baseline_numbers_24)
                sharedViewModel.filterByNumber = true
            }
        }
    }
}