package com.lr.androidfeature.customclasses

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacesItemDecoration(private val space: Int, private val isNormal: Boolean) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        outRect.top = space

        // Add left margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            if (isNormal) outRect.left = space else outRect.left = space * 3
        } else if (parent.getChildLayoutPosition(view) == state.itemCount - 1) {
            if (isNormal) outRect.right = space else outRect.right = space * 3
        } else {
            outRect.left = space
            outRect.right = space
        }
    }
}