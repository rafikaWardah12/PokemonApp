package com.example.pokemonapp

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokemonapp.data.models.PokemonListEntry
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.util.Constants.PAGE_SIZE
import com.example.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokemonListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    //StudyCase, Aku ingin membuat saat aku search, hint search nya jdi tulisan hasil research dn nampilin id name nya

    //save remote if we offline
    private var cachePokemonList = listOf<PokemonListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachePokemonList
        }
        //Supaya tidak membebani main tread untuk banyaknya list, krna itu sekalian diset kan dipindah ke default
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachePokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if (isSearchStarting){
                cachePokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
            isSearching.value  = true
        }
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    if (result.data?.count != null) {
                        //
                        endReached.value = currentPage * PAGE_SIZE >= result.data.count
                    }

                    result.data?.let { pokemonResult ->
                        //knp memakai mapIndexed
                        val pokemonEntries = pokemonResult.results?.mapIndexed { index, entry ->
                            entry?.url?.let {
                                //Buat apa sih?
                                val number = if (entry.url.endsWith("/")) {
                                    entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                                } else {
                                    entry.url.takeLastWhile { it.isDigit() }
                                }
                                val url =
                                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                                PokemonListEntry(
                                    entry.name!!.capitalize(Locale.ROOT),
                                    url,
                                    number.toInt()
                                )
                            }

                        }?.filterNotNull() ?: emptyList()  // Filter out null elements

                        currentPage++

                        loadError.value = " "
                        isLoading.value = false
                        pokemonList.value += pokemonEntries
                    }
                }
                //                    if (result.data?.results != null){
//                        val pokemonEntries = result.data.results.mapIndexed { index, entry ->
//                            if (entry?.url != null && entry?.name != null) {
//                                val number = if (entry.url.endsWith("/")) {
//                                    entry.url.dropLast(1).takeLastWhile { it.isDigit() }
//                                } else {
//                                    entry.url.takeLastWhile { it.isDigit() }
//                                }
//                                val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
//                                PokemonListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
//                            } else {
//                            }
//                        }


                is Resource.Error -> {
                    result?.message?.let {
                        loadError.value = result.message
                        isLoading.value = false

                    }

                }

                is Resource.Loading -> {

                }
            }
        }
    }
    fun calculateDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}