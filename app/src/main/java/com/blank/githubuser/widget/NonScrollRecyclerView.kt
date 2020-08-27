package com.blank.githubuser.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import com.mlsdev.animatedrv.AnimatedRecyclerView


class NonScrollRecyclerView : AnimatedRecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        isNestedScrollingEnabled = false
    }
}