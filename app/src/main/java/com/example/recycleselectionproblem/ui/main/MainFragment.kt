package com.example.recycleselectionproblem.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleselectionproblem.R
import com.example.recycleselectionproblem.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var tracker: SelectionTracker<String>
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        viewModel = createViewModel()
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.initViewModel()

        viewModel.items.observe(this, Observer { items ->
            val adapter = MyListAdapter()
            adapter.submitList(items)
            binding.recycleView.adapter = adapter

            trackSelectedItems(adapter, binding.recycleView)
            adapter.notifyDataSetChanged()
        })

        binding.btnGoToNextFragment.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToOtherFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun trackSelectedItems(
        adapter: MyListAdapter,
        recyclerView: RecyclerView
    ) {
        tracker = SelectionTracker.Builder<String>(
            "selectionTracker",
            recyclerView,
            ItemIdKeyProvider(adapter),
            ItemLookup(recyclerView),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()

        adapter.setTracker(tracker)

        tracker.addObserver(object : SelectionTracker.SelectionObserver<String>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
            }
        })
    }

    private fun createViewModel(): MainViewModel {
        val application = requireNotNull(this.activity).application
        val viewModelFactory =
            MainViewModelFactory(application)

        return ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

}