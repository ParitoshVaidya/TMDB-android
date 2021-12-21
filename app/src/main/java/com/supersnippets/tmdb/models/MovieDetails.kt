package com.supersnippets.tmdb.models


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val budget: Int,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val genres: List<Genre>,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("vote_count")
    val voteCount: Int
)

data class Genre(
    val id: Int,
    val name: String
)