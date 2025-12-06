package com.example.moviebrowser.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: OmdbApiService = retrofit.create(OmdbApiService::class.java)
}