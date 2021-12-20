package com.supersnippets.tmdb.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.supersnippets.tmdb.R
import com.supersnippets.tmdb.adapters.LoadingStateAdapter
import com.supersnippets.tmdb.adapters.MoviesAdapter
import com.supersnippets.tmdb.databinding.ActivityDashboardBinding
import com.supersnippets.tmdb.helpers.LOADING_ITEM
import com.supersnippets.tmdb.interfaces.OnItemClickedListener
import com.supersnippets.tmdb.models.Movie
import com.supersnippets.tmdb.ui.movieDetails.DetailsActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity(), OnItemClickedListener<Movie> {
    private val dashboardViewModel by viewModel<DashboardViewModel>()
    private lateinit var binding: ActivityDashboardBinding
    private val moviesAdapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.trending)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            adapter = moviesAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter()
            )
            moviesAdapter.addLoadStateListener { loadState ->
                binding.group.isVisible = false
                binding.progressBar.isVisible = false

                if (moviesAdapter.itemCount < 1) {
                    if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                        binding.progressBar.isVisible = true
                    } else {
                        binding.progressBar.isVisible = false
                        val errorState = when {
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }
                        errorState?.let {
                            binding.message.text = it.error.localizedMessage
                            binding.group.isVisible = true
                        }
                    }
                }
            }

            val gridLayoutManager = GridLayoutManager(this@DashboardActivity, 3)
            gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (moviesAdapter.getItemViewType(position) == LOADING_ITEM) 1 else 3
                }
            }
            layoutManager = gridLayoutManager
            moviesAdapter.setOnItemClickListener(this@DashboardActivity)
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            dashboardViewModel.movies.collectLatest {
                println("data received $it")
                moviesAdapter.submitData(it)
            }
        }
    }

    override fun onItemClicked(t: Movie) {
        println("Item clicked $t")
        val intent = Intent(this@DashboardActivity, DetailsActivity::class.java)
        intent.putExtra("id", t.id)
        startActivity(intent)
    }
}