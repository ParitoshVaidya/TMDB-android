package com.supersnippets.tmdb.di

import com.supersnippets.tmdb.helpers.ApiService
import com.supersnippets.tmdb.repositories.MoviesRepo
import com.supersnippets.tmdb.ui.dashboard.DashboardViewModel
import com.supersnippets.tmdb.ui.movieDetails.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { DashboardViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    single { provideMoviesRepo(get()) }
}

fun provideMoviesRepo(apiService: ApiService): MoviesRepo {
    return MoviesRepo(apiService)
}