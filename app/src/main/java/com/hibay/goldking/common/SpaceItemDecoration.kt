package com.hibay.goldking.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 *
 *
 * @author admin
 * @date 2021/5/11 14:49
 */

class SpaceItemDecoration(space: Int) : ItemDecoration() {
    private val space: Int = space
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, space)
    }

}