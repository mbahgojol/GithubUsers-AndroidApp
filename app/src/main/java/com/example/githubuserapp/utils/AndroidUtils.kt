package com.example.githubuserapp.utils

import android.widget.ImageView
import coil.ImageLoader
import coil.imageLoader
import coil.loadAny
import coil.request.Disposable
import coil.request.ImageRequest

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