package com.supersnippets.tmdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.supersnippets.tmdb.R
import com.supersnippets.tmdb.databinding.ItemMovieBinding
import com.supersnippets.tmdb.helpers.LOADING_ITEM
import com.supersnippets.tmdb.helpers.MOVIE_ITEM
import com.supersnippets.tmdb.helpers.POSTER_BASE_URL
import com.supersnippets.tmdb.interfaces.OnItemClickedListener
import com.supersnippets.tmdb.models.Movie

class MoviesAdapter :
    PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(MovieComparator) {
    lateinit var listener: OnItemClickedListener<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun getItemViewType(position: Int) =
        if (position == itemCount) MOVIE_ITEM else LOADING_ITEM

    fun setOnItemClickListener(onItemClickListener: OnItemClickedListener<Movie>) {
        listener = onItemClickListener
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    inner class MovieViewHolder(private val viewBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: Movie) {
            Glide.with(viewBinding.imageView.context)
                .load(POSTER_BASE_URL + item.posterPath)
                .placeholder(R.drawable.tmdb)
                .into(viewBinding.imageView)
            viewBinding.imageView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}