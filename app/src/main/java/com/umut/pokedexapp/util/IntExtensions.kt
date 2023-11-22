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

fun Int?.calculateProgress(): Int {
    return if (this != null) {
        this * 100 / Constants.HIGHEST_BASE_STAT
    } else {
        0
    }
}

fun Int?.formatStat(): String {
    return if (this==null){
        "000"
    } else {
        val formattedStat = when (this) {
            in 1..9 -> "00$this"
            in 10..99 -> "0$this"
            else -> "$this"
        }
        formattedStat
    }
}