package com.supersnippets.tmdb.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.supersnippets.tmdb.repositories.MoviesRepo

class DashboardViewModel(repository: MoviesRepo) : ViewModel() {
    val movies = repository.getMovies().cachedIn(viewModelScope)
}