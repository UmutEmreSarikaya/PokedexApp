package com.umut.pokedexapp.data.remote.service

import com.umut.pokedexapp.data.remote.dto.PokemonListDTO
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(): Response<PokemonListDTO>
}