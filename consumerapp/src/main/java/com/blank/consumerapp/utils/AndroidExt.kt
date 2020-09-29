package com.blank.consumerapp.utils

import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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