package com.umut.pokedexapp.domain.repository

import com.umut.pokedexapp.data.remote.dto.PokemonDetailDTO
import com.umut.pokedexapp.data.remote.dto.PokemonListDTO
import retrofit2.Response

interface PokemonRepository {
    suspend fun getPokemonList() : Response<PokemonListDTO>
    suspend fun getPokemonDetail(pokemonName:String) : Response<PokemonDetailDTO>
}