package com.umut.pokedexapp.domain.use_case.get_pokemon_detail

import com.umut.pokedexapp.data.remote.dto.toPokemonDetail
import com.umut.pokedexapp.domain.model.PokemonDetail
import com.umut.pokedexapp.domain.repository.PokemonRepository
import com.umut.pokedexapp.util.Resource
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    suspend fun executeGetPokemonDetail(pokemonName: String): Resource<PokemonDetail> {
        return try {
            val response = pokemonRepository.getPokemonDetail(pokemonName)
            if (response.isSuccessful) {
                response.body()?.let { pokemonDetailDTO ->
                    return@let Resource.success(pokemonDetailDTO.toPokemonDetail())
                } ?: Resource.error("Body Is Empty!", null)
            } else {
                Resource.error("Response Failed!", null)
            }
        } catch (e: Exception) {
            Resource.error("An Error Occurred!", null)
        }
    }
}