package com.blank.githubuser.utils

import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blank.githubuser.R
import com.bumptech.glide.Glide

fun ImageView.setImages(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, method: (T) -> Unit) {
    liveData.observe(this, Observer(method))
}

fun View.hide() = apply {
    this.visibility = View.GONE
}

fun View.show(): View = apply {
    this.visibility = View.VISIBLE
}

fun Menu.visibilitySearch(isvisible: Boolean) {
    setGroupVisible(R.id.groupSearch, isvisible)
}