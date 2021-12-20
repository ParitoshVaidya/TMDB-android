package com.supersnippets.tmdb.ui.movieDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.supersnippets.tmdb.R
import com.supersnippets.tmdb.databinding.ActivityDetailsBinding
import com.supersnippets.tmdb.helpers.POSTER_BASE_URL
import com.supersnippets.tmdb.helpers.convertDate
import com.supersnippets.tmdb.helpers.getDuration
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    private val detailsViewModel by viewModel<DetailsViewModel>()
    private lateinit var binding: ActivityDetailsBinding
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getIntExtra("id", 0)

        setSupportActionBar(findViewById(R.id.toolbar))

        setupUI()
        setupObservers()
    }

    private fun setupUI() {

    }

    private fun setupObservers() {
        detailsViewModel.getDetails(movieId).observe(this@DetailsActivity, {
            it?.let {
                println(it)
                binding.toolbarLayout.title = it.title

                Glide.with(this@DetailsActivity)
                    .load(POSTER_BASE_URL + it.posterPath)
                    .placeholder(R.drawable.tmdb)
                    .into(binding.imageView)

                binding.txtTitle.text = it.title
                binding.txtRuntime.text = getString(
                    R.string.release_date,
                    getDuration(it.runtime), convertDate(it.releaseDate)
                )
                binding.txtGenres.text = it.genres.joinToString { it.name }
                binding.rating.rating = (it.rating / 2).toFloat()
                binding.txtContent.text = it.overview
                binding.txtReviewCount.text = getString(R.string.review, it.voteCount)
            }
        })
    }
}