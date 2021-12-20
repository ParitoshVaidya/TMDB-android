package com.supersnippets.tmdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supersnippets.tmdb.databinding.LayoutLoadingStateBinding

class LoadingStateAdapter : LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutLoadingStateBinding.inflate(layoutInflater, parent, false)
        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val viewBinding: LayoutLoadingStateBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(loadState: LoadState) {

            viewBinding.txtErrorMessage.isVisible = loadState !is LoadState.Loading
            viewBinding.progress.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error) {
                viewBinding.txtErrorMessage.text =
                    loadState.error.localizedMessage
            }
        }
    }
}