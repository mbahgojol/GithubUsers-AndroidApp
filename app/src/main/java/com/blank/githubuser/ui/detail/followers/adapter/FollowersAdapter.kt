package com.blank.githubuser.ui.detail.followers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blank.githubuser.data.model.User
import com.blank.githubuser.databinding.ItemUserBinding
import com.blank.githubuser.utils.ANONYM_PERSON_ICON
import com.blank.githubuser.utils.noLocation
import com.blank.githubuser.utils.noName
import com.blank.githubuser.utils.setImages
import javax.inject.Inject

class FollowersAdapter @Inject constructor() :
    PagingDataAdapter<User, FollowersAdapter.FollowersViewHolder>(
        REPO_COMPARATOR
    ) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }


    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        getItem(position)?.let { user ->
            holder.bind(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FollowersViewHolder.create(
            parent
        )

    class FollowersViewHolder(private val v: ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        companion object {
            fun create(parent: ViewGroup) =
                FollowersViewHolder(
                    ItemUserBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }

        fun bind(user: User) {
            v.ivPhoto.setImages(user.avatarUrl ?: ANONYM_PERSON_ICON)
            v.tvTitle.text = user.name ?: v.root.context.noName()
            v.tvLocation.text = user.location ?: v.root.context.noLocation()
        }
    }
}