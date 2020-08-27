package com.blank.githubuser.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SmartNestedScrollView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    NestedScrollView(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    override fun measureChildWithMargins(
        child: View?,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ) {
        if (findNestedRecyclerView(child) != null) {
            val lp = child?.layoutParams as MarginLayoutParams
            val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                lp.topMargin + lp.bottomMargin, MeasureSpec.AT_MOST
            )
            child.measure(parentWidthMeasureSpec, childHeightMeasureSpec)
        } else {
            super.measureChildWithMargins(
                child,
                parentWidthMeasureSpec,
                widthUsed,
                parentHeightMeasureSpec,
                heightUsed
            )
        }
    }

    private fun findNestedRecyclerView(view: View?): RecyclerView? {
        when (view) {
            is RecyclerView -> {
                val vertical =
                    (view.layoutManager as? LinearLayoutManager)?.orientation == LinearLayoutManager.VERTICAL
                if (vertical) return view
            }
            is ViewGroup -> {
                view.forEach { child ->
                    val rv = findNestedRecyclerView(child)
                    if (rv != null) return rv
                }
            }
        }
        return null
    }
}