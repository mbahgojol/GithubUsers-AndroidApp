package com.blank.consumerapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blank.consumerapp.data.model.User
import com.blank.consumerapp.databinding.ItemUserBinding
import com.blank.consumerapp.utils.ANONYM_PERSON_ICON
import com.blank.consumerapp.utils.noLocation
import com.blank.consumerapp.utils.noName
import com.blank.consumerapp.utils.setImages
import kotlinx.android.synthetic.main.item_user.view.*
import javax.inject.Inject

class FavoriteAdapter @Inject constructor() :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val data: MutableList<User> = mutableListOf()

    private fun create(parent: ViewGroup) = FavoriteViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    fun setData(list: List<User>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        create(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    inner class FavoriteViewHolder(
        val v: ItemUserBinding
    ) : RecyclerView.ViewHolder(v.root) {

        fun bind(
            user: User
        ) {
            v.root.ivPhoto.setImages(user.avatarUrl ?: ANONYM_PERSON_ICON)
            v.root.tvTitle.text = user.name ?: v.root.context.noName()
            v.root.tvLocation.text = user.location ?: v.root.context.noLocation()
        }
    }
}