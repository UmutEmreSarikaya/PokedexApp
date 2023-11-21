package com.umut.pokedexapp.data.remote.dto

import com.umut.pokedexapp.domain.model.PokemonDetail

data class PokemonDetailDTO(
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<Any>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val past_abilities: List<Any>,
    val past_types: List<Any>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)

fun PokemonDetailDTO.toPokemonDetail(): PokemonDetail {
    var hp = 0
    var attack = 0
    var defense = 0
    var specialAttack = 0
    var specialDefense = 0
    var speed = 0

    val types: List<String> = types.map { type ->
        type.type.name
    }

    val moves: List<String> = moves.map { move ->
        move.move.name
    }
    stats.forEach { stat ->
        when (stat.stat.name) {
            "hp" -> {
                hp = stat.base_stat
            }

            "attack" -> {
                attack = stat.base_stat
            }

            "defense" -> {

                defense = stat.base_stat
            }

            "special-attack" -> {
                specialAttack = stat.base_stat
            }

            "special-defense" -> {
                specialDefense = stat.base_stat
            }

            "speed" -> {
                speed = stat.base_stat
            }
        }
    }

    return PokemonDetail(
        id = this.id,
        name = this.name,
        imageUrl = this.sprites.front_default,
        types = types,
        weight = this.weight,
        height = this.height,
        moves = moves,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed
    )
}