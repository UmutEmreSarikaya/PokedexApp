package com.umut.pokedexapp.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.umut.pokedexapp.R
import com.umut.pokedexapp.databinding.FragmentPokemonDetailBinding
import com.umut.pokedexapp.presentation.viewmodel.SharedViewModel
import com.umut.pokedexapp.util.PokemonType
import com.umut.pokedexapp.util.calculateProgress
import com.umut.pokedexapp.util.formatHeight
import com.umut.pokedexapp.util.formatStat
import com.umut.pokedexapp.util.formatWeight
import com.umut.pokedexapp.util.getPokemonIdFormatted
import com.umut.pokedexapp.util.removeNewLines
import com.umut.pokedexapp.util.viewGone
import com.umut.pokedexapp.util.viewVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    private lateinit var binding: FragmentPokemonDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("mine", sharedViewModel.filteredPokemonList.toString())
        initObservers()
        sharedViewModel.getPokemonDetail(sharedViewModel.pokemonName)
        binding.ibBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.pokemonDetailResource.collect { pokemonDetailResource ->
                val colorResource =
                    PokemonType.getColorResource(pokemonDetailResource.data?.types?.get(0))
                val detailColor = ContextCompat.getColor(requireContext(), colorResource)

                setDetailColor(detailColor)

                binding.apply {
                    innerLayout.viewVisible()
                    ivPokemon.viewVisible()

                    tvPokemonName.text =
                        pokemonDetailResource.data?.name?.replaceFirstChar { it.titlecase() }
                    tvPokemonId.text = pokemonDetailResource.data?.id?.getPokemonIdFormatted()
                    Glide.with(this@PokemonDetailFragment)
                        .load(pokemonDetailResource.data?.imageUrl).into(ivPokemon)

                    tvPokemonHp.text = pokemonDetailResource.data?.hp?.formatStat()
                    tvPokemonAtk.text = pokemonDetailResource.data?.attack?.formatStat()
                    tvPokemonDef.text = pokemonDetailResource.data?.defense?.formatStat()
                    tvPokemonSatk.text = pokemonDetailResource.data?.specialAttack?.formatStat()
                    tvPokemonSdef.text = pokemonDetailResource.data?.specialDefense?.formatStat()
                    tvPokemonSpd.text = pokemonDetailResource.data?.speed?.formatStat()

                    hpProgress.setProgressCompat(
                        pokemonDetailResource.data?.hp.calculateProgress(), true
                    )
                    atkProgress.setProgressCompat(
                        pokemonDetailResource.data?.attack.calculateProgress(), true
                    )
                    defProgress.setProgressCompat(
                        pokemonDetailResource.data?.defense.calculateProgress(), true
                    )
                    satkProgress.setProgressCompat(
                        pokemonDetailResource.data?.specialAttack.calculateProgress(), true
                    )
                    sdefProgress.setProgressCompat(
                        pokemonDetailResource.data?.specialDefense.calculateProgress(), true
                    )
                    spdProgress.setProgressCompat(
                        pokemonDetailResource.data?.speed.calculateProgress(), true
                    )

                    tvPokemonHeight.text = pokemonDetailResource.data?.height?.formatHeight()
                    tvPokemonWeight.text = pokemonDetailResource.data?.weight?.formatWeight()
                }

                pokemonDetailResource.data?.types?.forEach { tagName ->
                    val tagColorResource = PokemonType.getColorResource(tagName)
                    binding.chipGroup.addView(
                        createChip(
                            requireContext(), tagName, tagColorResource
                        )
                    )
                }

                pokemonDetailResource.data?.moves?.forEach { moveName ->
                    binding.movesLayout.addView(
                        createTextView(requireContext(),
                            moveName.replaceFirstChar { it.titlecase() })
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.pokemonDetailLoading.collect { loadingResource ->
                loadingResource.data?.let { loading ->
                    if (loading) {
                        binding.apply {
                            progressBar.viewVisible()
                            innerLayout.viewGone()
                            ivPokemon.viewGone()
                        }
                    } else {
                        binding.progressBar.viewGone()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.pokemonDetailError.collect { errorResource ->
                errorResource.data?.let { error ->
                    if (error) {
                        binding.apply {
                            tvError.viewVisible()
                            innerLayout.viewGone()
                            progressBar.viewGone()
                            outerLayout.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(), R.color.white
                                )
                            )
                        }
                    } else {
                        binding.tvError.viewGone()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.pokemonSpeciesResource.collect { pokemonSpeciesResource ->
                binding.tvDescription.text =
                    pokemonSpeciesResource.data?.flavorText?.removeNewLines()
            }
        }
    }

    private fun setDetailColor(color: Int) {
        activity?.window?.statusBarColor = color

        binding.apply {
            outerLayout.setBackgroundColor(color)
            tvAbout.setTextColor(color)
            tvBaseStats.setTextColor(color)

            tvHp.setTextColor(color)
            tvAtk.setTextColor(color)
            tvDef.setTextColor(color)
            tvSatk.setTextColor(color)
            tvSdef.setTextColor(color)
            tvSpd.setTextColor(color)

            hpProgress.setIndicatorColor(color)
            atkProgress.setIndicatorColor(color)
            defProgress.setIndicatorColor(color)
            satkProgress.setIndicatorColor(color)
            sdefProgress.setIndicatorColor(color)
            spdProgress.setIndicatorColor(color)
        }
    }

    private fun createChip(context: Context, typeName: String, colorResource: Int): Chip {
        return Chip(context).apply {
            val shapeAppearance = shapeAppearanceModel.withCornerSize(50f)
            text = typeName
            setChipBackgroundColorResource(colorResource)
            setChipStrokeColorResource(colorResource)
            setTextColor(ContextCompat.getColor(context, R.color.white))
            shapeAppearanceModel = shapeAppearance
        }
    }

    private fun createTextView(context: Context, moveName: String): TextView {
        return TextView(context).apply {
            text = moveName
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }
}