package com.umut.pokedexapp.domain.use_case.get_pokemon_species


import com.umut.pokedexapp.data.remote.dto.toPokemonSpecies
import com.umut.pokedexapp.domain.model.PokemonSpecies
import com.umut.pokedexapp.domain.repository.PokemonRepository
import com.umut.pokedexapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetPokemonSpeciesUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {
    fun executeGetPokemonSpecies(pokemonName: String): Flow<Resource<PokemonSpecies>> = flow {
        try {
            val response = pokemonRepository.getPokemonSpecies(pokemonName)
            if (response.isSuccessful) {
                response.body()?.let { pokemonSpeciesDTO ->
                    emit(Resource.success(pokemonSpeciesDTO.toPokemonSpecies()))
                } ?: emit(Resource.error("Body Is Empty!", null))
            } else {
                emit(Resource.error("Response Failed!", null))
            }
        } catch (e: IOError) {
            emit(Resource.error("No Internet Connection!", null))
        } catch (e: HttpException) {
            emit(Resource.error(e.localizedMessage ?: "An Error Occurred!", null))
        }
    }
}