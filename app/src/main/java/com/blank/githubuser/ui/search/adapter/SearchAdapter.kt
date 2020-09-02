package com.blank.githubuser.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
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
import kotlinx.android.synthetic.main.item_user.view.*
import javax.inject.Inject

class SearchAdapter @Inject constructor() :
    PagingDataAdapter<User, SearchAdapter.SearchViewHolder>(REPO_COMPARATOR) {

    private lateinit var listener: (User, viewArray: Array<View>) -> Unit

    fun setListener(listener: (user: User, viewArray: Array<View>) -> Unit) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { user ->
            holder.bind(user)
            holder.itemView.setOnClickListener {
                listener.invoke(
                    user,
                    arrayOf(
                        holder.itemView.ivPhoto,
                        holder.itemView.tvTitle,
                        holder.itemView.tvLocation
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder.create(
            parent
        )

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }

    class SearchViewHolder(private val v: ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        companion object {
            fun create(parent: ViewGroup) =
                SearchViewHolder(
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