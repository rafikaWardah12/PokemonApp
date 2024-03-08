package com.example.pokemonapp.repository

import com.example.pokemonapp.data.remote.PokeApi
import com.example.pokemonapp.data.remote.response.PokemonDetailResponse
import com.example.pokemonapp.data.remote.response.PokemonResponseList
import com.example.pokemonapp.util.Resource
import com.example.pokemonapp.util.Resource.Error
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    //offset = bnyknya kliapatan (Maximal nya)
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonResponseList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error(message = "Error Message")
        }
        return  Resource.Success(response)
    }

    suspend fun getPokemonDetail(pokemonName: String): Resource<PokemonDetailResponse>{
        val response = try {
            api.getPokemonDetail(pokemonName)
        } catch (e: Exception){
            return Resource.Error(message = "Unkown Error")
        }
        return Resource.Success(response)
    }
}