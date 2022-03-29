package com.hazz.kotlinmvp.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hazz.kotlinmvp.ui.adapter.HomeAdapter

class HomeDecoration(var spanCount: Int,var spanSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
         val position = parent.getChildAdapterPosition(view)
         if(parent.adapter?.getItemViewType(position) == HomeAdapter.ITEM_TYPE_CONTENT){
             val column = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
             outRect.left = (spanCount - column) * spanSize / spanCount
             outRect.right = (column + 1) * spanSize / spanCount
             outRect.bottom = spanSize
         }
    }
}