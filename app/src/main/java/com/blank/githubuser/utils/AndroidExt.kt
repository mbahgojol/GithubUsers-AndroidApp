package com.blank.githubuser.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blank.githubuser.R
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

fun Menu.hideSearch() {
    setGroupVisible(R.id.groupSearch, false)
}

fun Menu.showSearch() {
    setGroupVisible(R.id.groupSearch, true)
}

fun Menu.showSetting() {
    setGroupVisible(R.id.groupSetting, true)
}

fun Menu.hideSetting() {
    setGroupVisible(R.id.groupSetting, false)
}

fun Menu.hideFavorite() {
    findItem(R.id.favoriteFragment).isVisible = false
}

fun Menu.showFavorite() {
    findItem(R.id.favoriteFragment).isVisible = true
}

fun FloatingActionButton.changeColor(click: Boolean) {
    if (click) {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(Color.RED)
        )
    } else {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(Color.WHITE)
        )
    }
}