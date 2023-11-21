package com.umut.pokedexapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umut.pokedexapp.domain.model.Pokemon
import com.umut.pokedexapp.domain.use_case.get_pokemon_list.GetPokemonListUseCase
import com.umut.pokedexapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val getPokemonListUseCase: GetPokemonListUseCase) :
    ViewModel() {
    val pokemonListResource = MutableLiveData<Resource<List<Pokemon>>>()
    val pokemonListError = MutableLiveData<Resource<Boolean>>()
    val pokemonListLoading = MutableLiveData<Resource<Boolean>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.localizedMessage)
        pokemonListError.value = Resource.error(throwable.localizedMessage ?: "An Error Occurred!", true)
    }

    init {
        getPokemonList()
    }

     private fun getPokemonList() {
        pokemonListLoading.value = Resource.loading(true)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val resource = getPokemonListUseCase.executeGetPokemonList()
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    pokemonListLoading.value = Resource.loading(false)
                    pokemonListError.value = Resource.error("", false)
                    pokemonListResource.value = resource
                }
            }
        }
    }
}