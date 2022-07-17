package com.example.moviecompose.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("discover/movie")
    suspend fun getMovie(
        @Query("api_key") apiKey: String = "0d8dc32cd5815d5dcee726da63759530",
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>
}