package com.example.recycleselectionproblem.ui.main

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleselectionproblem.R

class MyListAdapter()
    : ListAdapter<MyModel, MyListAdapter.MyItemViewHolder>(DiffCallback()) {

    private var tracker: SelectionTracker<String>? = null
    fun setTracker(tracker: SelectionTracker<String>?) {
        this.tracker = tracker
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        return MyItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false),
            this
        )
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, tracker!!.isSelected(item.id))
    }

    class MyItemViewHolder(itemView: View, private val adapter: MyListAdapter) : RecyclerView.ViewHolder(itemView) {

        private var text: TextView? = null
        private var container: View? = null

        init {
            text = itemView.findViewById(R.id.text)
            container = itemView.findViewById(R.id.itemContainer)
        }

        fun bind(item: MyModel, selected: Boolean) {
            text?.text = item.name

            if (selected) {
                val theme = itemView.context!!.theme
                container?.setBackgroundColor(
                    itemView.context!!.resources.getColor(
                        android.R.color.darker_gray,
                        theme
                    )
                )
            } else {
                container?.setBackgroundColor(0)
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): String? = adapter.getItem(adapterPosition).id
                override fun inSelectionHotspot(e: MotionEvent): Boolean {
                    return true
                }
            }
    }

    class DiffCallback : DiffUtil.ItemCallback<MyModel>() {
        override fun areItemsTheSame(oldItem: MyModel, newItem: MyModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyModel, newItem: MyModel): Boolean {
            return oldItem == newItem
        }
    }
}