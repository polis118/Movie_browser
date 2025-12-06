package com.example.moviebrowser.data.remote

import com.example.moviebrowser.Constants
import com.example.moviebrowser.data.model.MovieDetail
import com.example.moviebrowser.data.model.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") title: String,
        @Query("apikey") apiKey: String = Constants.API_KEY
    ): MovieSearchResponse

    @GET("/")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String = Constants.API_KEY
    ): MovieDetail
}