package com.stebakov.setandget

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val result = MutableLiveData<String>()

    init {
        Log.d("ViewModel", "ViewModelCreated")
    }

    //view model destroy when activity destroyed (activity -> destroy => view model -> destroy)
    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "ViewModelDestroy")
    }

    fun save(text: String) {
        result.value = "Save result $text"
    }

    fun load() {
        result.value = "text loaded"
    }
}