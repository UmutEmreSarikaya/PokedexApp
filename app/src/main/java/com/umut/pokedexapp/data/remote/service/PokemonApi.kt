package com.umut.pokedexapp.data.remote.service

import com.umut.pokedexapp.data.remote.dto.PokemonDetailDTO
import com.umut.pokedexapp.data.remote.dto.PokemonListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(): Response<PokemonListDTO>

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetail(@Path("pokemonName") pokemonName: String): Response<PokemonDetailDTO>

}