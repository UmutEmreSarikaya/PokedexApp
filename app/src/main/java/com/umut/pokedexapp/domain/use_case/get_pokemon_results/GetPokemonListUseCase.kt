package com.umut.pokedexapp.domain.use_case.get_pokemon_results

import com.umut.pokedexapp.data.remote.dto.toPokemonList
import com.umut.pokedexapp.domain.model.Pokemon
import com.umut.pokedexapp.domain.repository.PokemonRepository
import com.umut.pokedexapp.util.Resource
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {
    suspend fun executeGetPokemonList(): Resource<List<Pokemon>> {
        return try {
            val response = pokemonRepository.getPokemonList()
            if (response.isSuccessful) {
                response.body()?.let {pokemonListDTO ->
                    return@let Resource.success(pokemonListDTO.toPokemonList())
                } ?: Resource.error("Body Is Empty!", null)
            } else {
                Resource.error("Response Failed!", null)
            }
        } catch (e: Exception) {
            Resource.error("An Error Occurred!", null)
        }
    }
}