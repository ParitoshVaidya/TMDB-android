package com.supersnippets.tmdb.helpers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.supersnippets.tmdb.models.Movie

class DataPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: FIRST_PAGE
            val movies = apiService.getMovies(nextPage)

            LoadResult.Page(
                data = movies.movieList,
                prevKey = if (nextPage == FIRST_PAGE) null else nextPage - 1,
                nextKey = if (movies.totalPages > nextPage) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }
}