package com.example.moviebrowser.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.data.model.Movie
import com.example.moviebrowser.data.repository.MovieRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repo = MovieRepository()

    var movies = mutableStateOf<List<Movie>>(emptyList())
    var isLoading = mutableStateOf(false)

    fun search(query: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val result = repo.searchMovies(query)
                movies.value = result.search
            } finally {
                isLoading.value = false
            }
        }
    }
}