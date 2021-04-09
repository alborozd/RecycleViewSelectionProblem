package com.example.recycleselectionproblem.ui.main

import android.app.Application
import androidx.lifecycle.*
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _items = MutableLiveData<List<MyModel>>()
    val items: LiveData<List<MyModel>>
        get() = _items

    fun initViewModel() {
        _items.postValue(
            listOf(
                MyModel("1", "Alex"),
                MyModel("2", "John"),
                MyModel("3", "Mike"),
                MyModel("4", "Fred"),
            )
        )
    }
}

class MainViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown view model class for MainViewModel")
    }
}