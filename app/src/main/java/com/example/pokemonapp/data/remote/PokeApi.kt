package com.example.pokemonapp.data.remote

import com.example.pokemonapp.data.remote.response.PokemonDetailResponse
import com.example.pokemonapp.data.remote.response.PokemonResponseList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    //pokemon list & detail pokemon
    //Butuh pageSize untuk membuat bnyk nya load dlm 1 konek internet
    //Nilai awal 0 Max 20
    //limit = pageSize
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponseList

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDetailResponse
}