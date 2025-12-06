package com.example.moviebrowser.data.repository

import com.example.moviebrowser.data.remote.RetrofitInstance

class MovieRepository {
    private val api = RetrofitInstance.api

    suspend fun searchMovies(query: String) = api.searchMovies(query)
    suspend fun getMovieDetail(id: String) = api.getMovieDetail(id)
}
