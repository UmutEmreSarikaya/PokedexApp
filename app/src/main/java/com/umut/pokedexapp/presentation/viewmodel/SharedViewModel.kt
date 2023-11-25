package com.umut.pokedexapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umut.pokedexapp.domain.model.Pokemon
import com.umut.pokedexapp.domain.model.PokemonDetail
import com.umut.pokedexapp.domain.model.PokemonSpecies
import com.umut.pokedexapp.domain.use_case.get_pokemon_detail.GetPokemonDetailUseCase
import com.umut.pokedexapp.domain.use_case.get_pokemon_list.GetPokemonListUseCase
import com.umut.pokedexapp.domain.use_case.get_pokemon_species.GetPokemonSpeciesUseCase
import com.umut.pokedexapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val getPokemonSpeciesUseCase: GetPokemonSpeciesUseCase
) : ViewModel() {
    private val _pokemonListResource = MutableSharedFlow<Resource<List<Pokemon>>>()
    val pokemonListResource = _pokemonListResource.asSharedFlow()
    private val _pokemonListError = MutableStateFlow(Resource.error("", false))
    val pokemonListError = _pokemonListError.asStateFlow()
    private val _pokemonListLoading = MutableStateFlow(Resource.loading(false))
    val pokemonListLoading = _pokemonListLoading.asStateFlow()

    private val _pokemonDetailResource = MutableSharedFlow<Resource<PokemonDetail>>()
    val pokemonDetailResource = _pokemonDetailResource.asSharedFlow()
    private val _pokemonDetailError = MutableStateFlow(Resource.error("", false))
    val pokemonDetailError = _pokemonDetailError.asStateFlow()
    private val _pokemonDetailLoading = MutableStateFlow(Resource.loading(false))
    val pokemonDetailLoading = _pokemonDetailLoading.asStateFlow()

    private val _pokemonSpeciesResource = MutableSharedFlow<Resource<PokemonSpecies>>()
    val pokemonSpeciesResource = _pokemonSpeciesResource.asSharedFlow()

    var unfilteredPokemonList = listOf<Pokemon>()
    var filteredPokemonList = listOf<Pokemon>()
    var pokemonName = ""
    var filterByNumber = true


    private val listExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.localizedMessage)
        _pokemonListError.value =
            Resource.error(throwable.localizedMessage ?: "An Error Occurred!", true)
    }

    private val detailExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.localizedMessage)
        _pokemonDetailError.value =
            Resource.error(throwable.localizedMessage ?: "An Error Occurred!", true)
    }

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        _pokemonListLoading.value = Resource.loading(true)
        viewModelScope.launch(Dispatchers.IO + listExceptionHandler) {
            getPokemonListUseCase.executeGetPokemonList().collect {
                _pokemonListResource.emit(it)
            }
            withContext(Dispatchers.Main) {
                _pokemonListLoading.value = Resource.loading(false)
                _pokemonListError.value = Resource.error("", false)
            }
        }
    }

    fun getPokemonDetail(pokemonName: String) {
        _pokemonDetailLoading.value = Resource.loading(true)

        viewModelScope.launch(Dispatchers.IO + detailExceptionHandler) {
            getPokemonDetailUseCase.executeGetPokemonDetail(pokemonName).collect {
                _pokemonDetailResource.emit(it)
            }

            getPokemonSpeciesUseCase.executeGetPokemonSpecies(pokemonName).collect {
                _pokemonSpeciesResource.emit(it)
            }
            withContext(Dispatchers.Main) {
                _pokemonDetailLoading.value = Resource.loading(false)
                _pokemonDetailError.value = Resource.error("", false)
            }
        }
    }
}