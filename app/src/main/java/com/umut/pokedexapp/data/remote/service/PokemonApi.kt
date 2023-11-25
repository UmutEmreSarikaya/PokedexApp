package com.umut.pokedexapp.data.remote.service

import com.umut.pokedexapp.data.remote.dto.PokemonDetailDTO
import com.umut.pokedexapp.data.remote.dto.PokemonListDTO
import com.umut.pokedexapp.data.remote.dto.PokemonSpeciesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = 1292): Response<PokemonListDTO>

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetail(@Path("pokemonName") pokemonName: String): Response<PokemonDetailDTO>

    @GET("pokemon-species/{pokemonName}")
    suspend fun getPokemonSpecies(@Path("pokemonName") pokemonName: String): Response<PokemonSpeciesDTO>

}