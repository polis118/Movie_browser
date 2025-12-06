package com.example.moviebrowser.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.data.model.MovieDetail
import com.example.moviebrowser.data.repository.MovieRepository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val repo = MovieRepository()

    var movie = mutableStateOf<MovieDetail?>(null)
    var isLoading = mutableStateOf(false)

    fun loadMovie(id: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                movie.value = repo.getMovieDetail(id)
            } finally {
                isLoading.value = false
            }
        }
    }
}