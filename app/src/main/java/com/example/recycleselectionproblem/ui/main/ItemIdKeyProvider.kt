package com.example.recycleselectionproblem.ui.main

import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

class ItemIdKeyProvider(
    private val adapter: MyListAdapter
) : ItemKeyProvider<String>(SCOPE_MAPPED) {

    override fun getKey(position: Int): String? {
        return adapter.currentList[position].id
    }

    override fun getPosition(key: String): Int {
        return adapter.currentList.indexOfFirst { c -> c.id == key }
    }
}