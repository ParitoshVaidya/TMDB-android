package com.supersnippets.tmdb.ui.movieDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.supersnippets.tmdb.R
import com.supersnippets.tmdb.databinding.ActivityDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    private val detailsViewModel by viewModel<DetailsViewModel>()
    private lateinit var binding: ActivityDetailsBinding
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        movieId = intent.getIntExtra("id", 0)

        setupObservers()
    }

    private fun setupObservers() {
        detailsViewModel.getDetails(movieId).observe(this@DetailsActivity, {
            it?.let {
                binding.movieDetails = it
                binding.txtGenres.text = it.genres.joinToString { genre -> genre.name }
            }
        })
    }
}