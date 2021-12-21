package com.supersnippets.tmdb.helpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.supersnippets.tmdb.R

@BindingAdapter("loadImageUrl")
fun ImageView.loadImageUrl(url: String?) {
    Glide.with(this.context)
        .load(POSTER_BASE_URL + url)
        .placeholder(R.drawable.tmdb)
        .into(this)
}