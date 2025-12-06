package com.example.moviebrowser.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String
)
