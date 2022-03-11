package com.example.githubuserapp.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.imageLoader
import coil.loadAny
import coil.request.Disposable
import coil.request.ImageRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

inline fun ImageView.loadImg(
    uri: String?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    return loadAny(uri ?: NoImage, imageLoader, builder)
}

fun text(conten: Any?): StringBuilder = StringBuilder()
    .append(conten)

fun StringBuilder.space() = append(" ")

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    val color = ContextCompat.getColor(this.context, color)
    imageTintList = ColorStateList.valueOf(color)
}

fun View.showSnakBar(msg: String) {
    val snackbar = Snackbar.make(
        this,
        msg,
        Snackbar.LENGTH_INDEFINITE
    )
    snackbar.setAction("TUTUP") {
        snackbar.dismiss()
    }
    snackbar.show()
}