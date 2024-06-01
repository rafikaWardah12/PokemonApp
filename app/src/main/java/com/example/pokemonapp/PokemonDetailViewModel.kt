package com.example.pokemonapp

import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.remote.response.PokemonDetailResponse
import com.example.pokemonapp.domain.repository.PokemonRepository
import com.example.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonDetail(pokemonName: String): Resource<PokemonDetailResponse>{
        return repository.getPokemonDetail(pokemonName)

    }
}