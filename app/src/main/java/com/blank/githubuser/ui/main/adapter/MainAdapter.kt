package com.blank.githubuser.ui.main.adapter

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
import com.blank.githubuser.utils.setImages
import kotlinx.android.synthetic.main.item_user.view.*
import javax.inject.Inject

class MainAdapter @Inject constructor() : PagingDataAdapter<User, MainViewHolder>(
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

    private lateinit var listener: (User, viewArray: Array<View>) -> Unit

    fun setListener(listener: (user: User, viewArray: Array<View>) -> Unit) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
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
        MainViewHolder.create(
            parent
        )
}

class MainViewHolder(private val v: ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
    companion object {
        fun create(parent: ViewGroup) =
            MainViewHolder(
                ItemUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    fun bind(user: User) {
        v.tvTitle.text = user.name
        v.tvLocation.text = user.location ?: v.root.context.noLocation()
        v.ivPhoto.setImages(user.avatarUrl ?: ANONYM_PERSON_ICON)
    }
}
