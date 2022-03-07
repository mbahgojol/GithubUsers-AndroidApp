package com.example.githubuserapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuserapp.data.model.DetailUserResponse
import com.example.githubuserapp.databinding.ItemUserBinding

class UserAdapter(
    private val onClick: (DetailUserResponse) -> Unit
) : ListAdapter<DetailUserResponse, UserAdapter.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val v: ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun onBind(model: DetailUserResponse) {
            v.image.load(model.avatarUrl) {
                transformations(CircleCropTransformation())
            }
            v.name.text = model.name
            v.tvRepositori.text = model.publicRepos.toString()
            v.tvCorporate.text = model.company
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<DetailUserResponse>() {
            override fun areItemsTheSame(
                oldItem: DetailUserResponse,
                newItem: DetailUserResponse
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: DetailUserResponse,
                newItem: DetailUserResponse
            ): Boolean =
                oldItem == newItem
        }
    }
}