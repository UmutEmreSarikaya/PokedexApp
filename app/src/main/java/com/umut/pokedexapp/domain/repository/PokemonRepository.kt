package com.umut.pokedexapp.domain.repository

import com.umut.pokedexapp.data.remote.dto.PokemonListDTO
import retrofit2.Response

interface PokemonRepository {
    suspend fun getPokemonList() : Response<PokemonListDTO>
}