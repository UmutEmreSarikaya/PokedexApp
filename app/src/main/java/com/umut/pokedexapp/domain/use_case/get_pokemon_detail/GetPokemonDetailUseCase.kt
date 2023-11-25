package com.umut.pokedexapp.domain.use_case.get_pokemon_detail

import com.umut.pokedexapp.data.remote.dto.toPokemonDetail
import com.umut.pokedexapp.domain.model.PokemonDetail
import com.umut.pokedexapp.domain.repository.PokemonRepository
import com.umut.pokedexapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {
    fun executeGetPokemonDetail(pokemonName: String): Flow<Resource<PokemonDetail>> = flow {
        try {
            val response = pokemonRepository.getPokemonDetail(pokemonName)
            if (response.isSuccessful) {
                response.body()?.let { pokemonDetailDTO ->
                    emit(Resource.success(pokemonDetailDTO.toPokemonDetail()))
                } ?: emit(Resource.error("Body Is Empty!", null))
            } else {
                emit(Resource.error("Response Failed!", null))
            }
        } catch (e: IOError) {
            emit(Resource.error("No Internet Connection!!", null))
        } catch (e: HttpException) {
            emit(Resource.error(e.localizedMessage ?: "An Error Occurred!", null))
        }
    }
}