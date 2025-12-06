package com.example.moviebrowser.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.data.model.Movie
import com.example.moviebrowser.data.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repo = MovieRepository()

    var mostWatchedMovies = mutableStateOf<List<Movie>>(emptyList())
    var popularMovies = mutableStateOf<List<Movie>>(emptyList())
    var newMovies = mutableStateOf<List<Movie>>(emptyList())
    var searchResults = mutableStateOf<List<Movie>>(emptyList())

    var isLoading = mutableStateOf(false)
    var isSearching = mutableStateOf(false)

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            isLoading.value = true
            // Načítání urychlíme paralelním spuštěním
            val mostWatchedDeferred = async { repo.searchMovies("Marvel") }
            val popularDeferred = async { repo.searchMovies("Lord of the Rings") }
            val newDeferred = async { repo.searchMovies("Dune") }

            try {
                val mostWatchedResult = mostWatchedDeferred.await()
                if (mostWatchedResult.response == "True") {
                    mostWatchedMovies.value = mostWatchedResult.search
                }

                val popularResult = popularDeferred.await()
                if (popularResult.response == "True") {
                    popularMovies.value = popularResult.search
                }

                val newResult = newDeferred.await()
                if (newResult.response == "True") {
                    newMovies.value = newResult.search
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    fun search(query: String) {
        if (query.isBlank()) {
            isSearching.value = false
            searchResults.value = emptyList()
            return
        }
        
        isSearching.value = true
        viewModelScope.launch {
            isLoading.value = true
            try {
                val result = repo.searchMovies(query)
                if (result.response == "True") {
                    searchResults.value = result.search
                } else {
                    searchResults.value = emptyList()
                }
            } finally {
                isLoading.value = false
            }
        }
    }
}