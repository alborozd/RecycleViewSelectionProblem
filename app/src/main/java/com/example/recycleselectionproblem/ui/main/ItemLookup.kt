package com.example.recycleselectionproblem.ui.main

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class ItemLookup(private val rv: RecyclerView) : ItemDetailsLookup<String>() {
    override fun getItemDetails(event: MotionEvent)
            : ItemDetails<String>? {

        val view = rv.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (rv.getChildViewHolder(view) as MyListAdapter.MyItemViewHolder)
                .getItemDetails()
        }
        return null
    }
}