package com.example.pokemonapp.util

import androidx.compose.ui.graphics.Color
import com.example.pokemonapp.data.remote.response.Stat
import com.example.pokemonapp.data.remote.response.TypesItem
import com.example.pokemonapp.ui.theme.AtkColor
import com.example.pokemonapp.ui.theme.DefColor
import com.example.pokemonapp.ui.theme.HPColor
import com.example.pokemonapp.ui.theme.SpAtkColor
import com.example.pokemonapp.ui.theme.SpDefColor
import com.example.pokemonapp.ui.theme.SpdColor
import com.example.pokemonapp.ui.theme.TypeBug
import com.example.pokemonapp.ui.theme.TypeDark
import com.example.pokemonapp.ui.theme.TypeDragon
import com.example.pokemonapp.ui.theme.TypeElectric
import com.example.pokemonapp.ui.theme.TypeFairy
import com.example.pokemonapp.ui.theme.TypeFighting
import com.example.pokemonapp.ui.theme.TypeFire
import com.example.pokemonapp.ui.theme.TypeFlying
import com.example.pokemonapp.ui.theme.TypeGhost
import com.example.pokemonapp.ui.theme.TypeGrass
import com.example.pokemonapp.ui.theme.TypeGround
import com.example.pokemonapp.ui.theme.TypeIce
import com.example.pokemonapp.ui.theme.TypeNormal
import com.example.pokemonapp.ui.theme.TypePoison
import com.example.pokemonapp.ui.theme.TypePsychic
import com.example.pokemonapp.ui.theme.TypeRock
import com.example.pokemonapp.ui.theme.TypeSteel
import com.example.pokemonapp.ui.theme.TypeWater
import java.util.Locale

fun parseTypeColor(type: TypesItem?): Color {
    return when (type?.type!!.name!!.toLowerCase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color{
    return when(stat.name?.toLowerCase()){
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String{
    return when(stat.name?.toLowerCase()){
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpdAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}