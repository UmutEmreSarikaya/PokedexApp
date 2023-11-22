package com.umut.pokedexapp.util

import com.umut.pokedexapp.R

enum class PokemonType(private val type:String, private val color: Int) {
    NORMAL("normal", R.color.type_normal),
    FIRE("normal", R.color.type_fire),
    WATER("normal", R.color.type_water),
    ELECTRIC("normal", R.color.type_electric),
    GRASS("normal", R.color.type_grass),
    ICE("normal", R.color.type_ice),
    FIGHTING("normal", R.color.type_fighting),
    POISON("normal", R.color.type_poison),
    GROUND("normal", R.color.type_ground),
    FLYING("normal", R.color.type_flying),
    PSYCHIC("psychic", R.color.type_psychic),
    BUG("normal", R.color.type_bug),
    ROCK("normal", R.color.type_rock),
    GHOST("normal", R.color.type_ghost),
    DRAGON("normal", R.color.type_dragon),
    DARK("normal", R.color.type_dark),
    STEEL("normal", R.color.type_steel),
    FAIRY("normal", R.color.type_fairy);

    companion object {
        fun getColorResource(type: String?): Int {
            return values().find { it.type == type }?.color ?: R.color.black
        }
    }
}