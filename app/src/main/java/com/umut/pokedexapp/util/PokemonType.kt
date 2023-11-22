package com.umut.pokedexapp.util

import com.umut.pokedexapp.R

enum class PokemonType(private val type:String, private val color: Int) {
    NORMAL("normal", R.color.type_normal),
    FIRE("fire", R.color.type_fire),
    WATER("water", R.color.type_water),
    ELECTRIC("electric", R.color.type_electric),
    GRASS("grass", R.color.type_grass),
    ICE("ice", R.color.type_ice),
    FIGHTING("fighting", R.color.type_fighting),
    POISON("poison", R.color.type_poison),
    GROUND("ground", R.color.type_ground),
    FLYING("flying", R.color.type_flying),
    PSYCHIC("psychic", R.color.type_psychic),
    BUG("bug", R.color.type_bug),
    ROCK("rock", R.color.type_rock),
    GHOST("ghost", R.color.type_ghost),
    DRAGON("dragon", R.color.type_dragon),
    DARK("dark", R.color.type_dark),
    STEEL("steel", R.color.type_steel),
    FAIRY("fairy", R.color.type_fairy);

    companion object {
        fun getColorResource(type: String?): Int {
            return values().find { it.type == type }?.color ?: R.color.black
        }
    }
}