package com.umut.pokedexapp.data.remote.dto

import com.umut.pokedexapp.domain.model.Pokemon

data class PokemonListDTO(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)

fun PokemonListDTO.toPokemonList(): List<Pokemon>{
    return results.map { result -> Pokemon(name = result.name, url = result.url) }
}