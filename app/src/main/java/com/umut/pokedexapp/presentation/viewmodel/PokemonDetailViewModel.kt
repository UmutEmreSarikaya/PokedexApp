package com.umut.pokedexapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umut.pokedexapp.domain.model.Pokemon
import com.umut.pokedexapp.domain.model.PokemonDetail
import com.umut.pokedexapp.domain.use_case.get_pokemon_detail.GetPokemonDetailUseCase
import com.umut.pokedexapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val getPokemonDetailUseCase: GetPokemonDetailUseCase) : ViewModel() {
    val pokemonDetailResource = MutableLiveData<Resource<PokemonDetail>>()
    val pokemonListError = MutableLiveData<Resource<Boolean>>()
    val pokemonListLoading = MutableLiveData<Resource<Boolean>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.localizedMessage)
        pokemonListError.value = Resource.error(throwable.localizedMessage ?: "An Error Occurred!", true)
    }

    private fun getPokemonDetail(pokemonName: String) {
        pokemonListLoading.value = Resource.loading(true)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val resource = getPokemonDetailUseCase.executeGetPokemonDetail(pokemonName)
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    pokemonListLoading.value = Resource.loading(false)
                    pokemonListError.value = Resource.error("", false)
                    pokemonDetailResource.value = resource
                }
            }
        }
    }
}