package com.example.moviebrowser.data.model

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("Search") val search: List<Movie>? = null,
    @SerializedName("totalResults") val totalResults: String? = null,
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String? = null
)
