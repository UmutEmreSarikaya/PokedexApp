package com.umut.pokedexapp.domain.use_case.get_pokemon_list

import com.umut.pokedexapp.data.remote.dto.toPokemonList
import com.umut.pokedexapp.domain.model.Pokemon
import com.umut.pokedexapp.domain.repository.PokemonRepository
import com.umut.pokedexapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {
    fun executeGetPokemonList(): Flow<Resource<List<Pokemon>>> = flow {
        try {
            val response = pokemonRepository.getPokemonList()
            if (response.isSuccessful) {
                response.body()?.let { pokemonListDTO ->
                    emit(Resource.success(pokemonListDTO.toPokemonList()))
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