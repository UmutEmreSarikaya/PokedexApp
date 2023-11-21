package com.umut.pokedexapp.data.repository

import com.umut.pokedexapp.data.remote.dto.PokemonDetailDTO
import com.umut.pokedexapp.data.remote.dto.PokemonListDTO
import com.umut.pokedexapp.data.remote.service.PokemonApi
import com.umut.pokedexapp.domain.repository.PokemonRepository
import retrofit2.Response
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val pokemonApi: PokemonApi) : PokemonRepository {
    override suspend fun getPokemonList(): Response<PokemonListDTO> {
        return pokemonApi.getPokemonList()
    }

    override suspend fun getPokemonDetail(pokemonName:String): Response<PokemonDetailDTO> {
        return pokemonApi.getPokemonDetail(pokemonName)
    }
}