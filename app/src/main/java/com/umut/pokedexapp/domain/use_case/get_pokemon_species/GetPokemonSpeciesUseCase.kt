package com.umut.pokedexapp.domain.use_case.get_pokemon_species


import com.umut.pokedexapp.data.remote.dto.toPokemonSpecies
import com.umut.pokedexapp.domain.model.PokemonSpecies
import com.umut.pokedexapp.domain.repository.PokemonRepository
import com.umut.pokedexapp.util.Resource
import javax.inject.Inject

class GetPokemonSpeciesUseCase @Inject constructor(private val pokemonRepository: PokemonRepository){
    suspend fun executeGetPokemonSpecies(pokemonName: String): Resource<PokemonSpecies> {
        return try {
            val response = pokemonRepository.getPokemonSpecies(pokemonName)
            if (response.isSuccessful) {
                response.body()?.let { pokemonSpeciesDTO ->
                    return@let Resource.success(pokemonSpeciesDTO.toPokemonSpecies())
                } ?: Resource.error("Body Is Empty!", null)
            } else {
                Resource.error("Response Failed!", null)
            }
        } catch (e: Exception) {
            Resource.error("An Error Occurred!", null)
        }
    }
}