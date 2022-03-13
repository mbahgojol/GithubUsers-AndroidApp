package com.example.githubuserapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.databinding.ItemUserBinding

class UserAdapter(
    private val onClick: (User) -> Unit
) : ListAdapter<User, UserAdapter.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val item = currentList[position]
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val v: ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun onBind(model: User) {
            v.image.load(model.avatar_url) {
                transformations(CircleCropTransformation())
            }
            v.name.text = model.name
            v.tvRepositori.text = model.public_repos.toString()
            v.tvCorporate.text = model.company
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean =
                oldItem == newItem
        }
    }
}