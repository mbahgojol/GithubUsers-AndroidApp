package com.blank.githubuser.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blank.githubuser.databinding.ItemLoadstateFooterUserBinding

class MainLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MainLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: MainLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = MainLoadStateViewHolder.create(parent, retry)
}

class MainLoadStateViewHolder(
    private val binding: ItemLoadstateFooterUserBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error)
            binding.errorMsg.text = loadState.error.localizedMessage

        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit) = MainLoadStateViewHolder(
            ItemLoadstateFooterUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), retry
        )
    }
}