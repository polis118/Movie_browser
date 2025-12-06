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
    var error = mutableStateOf<String?>(null)

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            val mostWatchedDeferred = async { repo.searchMovies("Marvel") }
            val popularDeferred = async { repo.searchMovies("Lord of the Rings") }
            val newDeferred = async { repo.searchMovies("Dune") }

            try {
                val mostWatchedResult = mostWatchedDeferred.await()
                if (mostWatchedResult.response == "True") {
                    mostWatchedMovies.value = mostWatchedResult.search ?: emptyList()
                } else {
                    throw Exception(mostWatchedResult.error)
                }

                val popularResult = popularDeferred.await()
                if (popularResult.response == "True") {
                    popularMovies.value = popularResult.search ?: emptyList()
                } else {
                    throw Exception(popularResult.error)
                }

                val newResult = newDeferred.await()
                if (newResult.response == "True") {
                    newMovies.value = newResult.search ?: emptyList()
                } else {
                    throw Exception(newResult.error)
                }
            } catch (e: Exception) {
                error.value = e.message ?: "An unknown error occurred."
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
            error.value = null
            try {
                val result = repo.searchMovies(query)
                if (result.response == "True") {
                    searchResults.value = result.search ?: emptyList()
                } else {
                    searchResults.value = emptyList() // Clear results on API error
                }
            } catch (e: Exception) {
                error.value = e.message ?: "An unknown error occurred."
                searchResults.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }
}