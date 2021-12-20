package com.supersnippets.tmdb.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.supersnippets.tmdb.helpers.ApiService
import com.supersnippets.tmdb.helpers.DataPagingSource
import com.supersnippets.tmdb.helpers.PAGE_SIZE
import com.supersnippets.tmdb.models.Movie
import com.supersnippets.tmdb.models.MovieDetails
import kotlinx.coroutines.flow.Flow

class MoviesRepo(private val apiService: ApiService) {
    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { DataPagingSource(apiService) }
        ).flow
    }

    suspend fun getDetails(id: Int): MovieDetails {
        return apiService.getMovieDetails(id)
    }
}
