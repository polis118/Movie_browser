package com.example.moviebrowser.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Actors") val actors: String
)
