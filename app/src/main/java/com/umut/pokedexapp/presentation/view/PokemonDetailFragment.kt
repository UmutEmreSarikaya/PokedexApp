package com.umut.pokedexapp.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.umut.pokedexapp.R
import com.umut.pokedexapp.databinding.FragmentPokemonDetailBinding
import com.umut.pokedexapp.presentation.viewmodel.PokemonDetailViewModel
import com.umut.pokedexapp.util.PokemonType
import com.umut.pokedexapp.util.calculateProgress
import com.umut.pokedexapp.util.formatStat
import com.umut.pokedexapp.util.getPokemonIdFormatted
import com.umut.pokedexapp.util.removeNewLines
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private val pokemonDetailViewModel: PokemonDetailViewModel by viewModels()
    private lateinit var binding: FragmentPokemonDetailBinding
    private val safeArgs: PokemonDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonDetailViewModel.getPokemonDetail(safeArgs.name)
        initObservers()
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initObservers() {
        pokemonDetailViewModel.pokemonDetailResource.observe(viewLifecycleOwner) { pokemonDetailResource ->
            val colorResource =
                PokemonType.getColorResource(pokemonDetailResource.data?.types?.get(0))
            val detailColor = ContextCompat.getColor(requireContext(), colorResource)

            setDetailColor(detailColor)

            binding.apply {
                tvPokemonName.text =
                    pokemonDetailResource.data?.name?.replaceFirstChar { it.titlecase() }
                tvPokemonId.text = pokemonDetailResource.data?.id?.getPokemonIdFormatted()
                Glide.with(this@PokemonDetailFragment).load(pokemonDetailResource.data?.imageUrl)
                    .into(ivPokemon)

                tvPokemonHp.text = pokemonDetailResource.data?.hp.formatStat()
                tvPokemonAtk.text = pokemonDetailResource.data?.attack.formatStat()
                tvPokemonDef.text = pokemonDetailResource.data?.defense.formatStat()
                tvPokemonSatk.text = pokemonDetailResource.data?.specialAttack.formatStat()
                tvPokemonSdef.text = pokemonDetailResource.data?.specialDefense.formatStat()
                tvPokemonSpd.text = pokemonDetailResource.data?.speed.formatStat()

                hpProgress.setProgressCompat(pokemonDetailResource.data?.hp.calculateProgress(), true)
                atkProgress.setProgressCompat(pokemonDetailResource.data?.attack.calculateProgress(), true)
                defProgress.setProgressCompat(pokemonDetailResource.data?.defense.calculateProgress(), true)
                satkProgress.setProgressCompat(pokemonDetailResource.data?.specialAttack.calculateProgress(), true)
                sdefProgress.setProgressCompat(pokemonDetailResource.data?.specialDefense.calculateProgress(), true)
                spdProgress.setProgressCompat(pokemonDetailResource.data?.speed.calculateProgress(), true)
            }

            pokemonDetailResource.data?.types?.forEach { tagName ->
                val tagColorResource = PokemonType.getColorResource(tagName)
                binding.chipGroup.addView(createChip(requireContext(), tagName, tagColorResource))
            }
        }

        pokemonDetailViewModel.pokemonListLoading.observe(viewLifecycleOwner) {

        }

        pokemonDetailViewModel.pokemonListError.observe(viewLifecycleOwner) {

        }

        pokemonDetailViewModel.pokemonSpeciesResource.observe(viewLifecycleOwner) { pokemonSpeciesResource ->
            binding.tvDescription.text = pokemonSpeciesResource.data?.flavorText?.removeNewLines()
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

    private fun createChip(context: Context, chipName: String, colorResource:Int): Chip {
        return Chip(context).apply {
            text = chipName
            setChipBackgroundColorResource(colorResource)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}