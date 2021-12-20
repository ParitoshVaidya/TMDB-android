package com.supersnippets.tmdb.helpers

import com.supersnippets.tmdb.models.MovieDetails
import com.supersnippets.tmdb.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getMovies(@Query("page") page: Int): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): MovieDetails
}