package com.umut.pokedexapp.util


fun String.extractIdFromUrl(): Int {
    val parts = this.split("/")
    return parts[parts.size - 2].toInt()
}
