package com.example.moviecompose.remote

import com.example.moviecompose.model.Movie

data class MovieResponse(
    val page: Long,
    val results: List<Movie>,
)