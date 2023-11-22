package com.umut.pokedexapp.util

class StringExtensions {
    fun String.extractIdFromUrl(): Int {
        val parts = this.split("/")
        return parts[parts.size - 2].toInt()
    }
}