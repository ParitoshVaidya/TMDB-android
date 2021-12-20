package com.supersnippets.tmdb.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.supersnippets.tmdb.repositories.MoviesRepo
import kotlinx.coroutines.Dispatchers.IO

class DetailsViewModel(private val repository: MoviesRepo) : ViewModel() {
    fun getDetails(id: Int) = liveData(IO) {
        emit(repository.getDetails(id))
    }
}