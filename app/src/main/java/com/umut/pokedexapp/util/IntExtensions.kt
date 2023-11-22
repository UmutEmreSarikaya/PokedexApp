package com.umut.pokedexapp.util

fun Int.getPokemonIdFormatted(): String {
    val formattedId = when (this) {
        in 1..9 -> "#000$this"
        in 10..99 -> "#00$this"
        in 100..999 -> "#0$this"
        else -> "#$this"
    }
    return formattedId
}