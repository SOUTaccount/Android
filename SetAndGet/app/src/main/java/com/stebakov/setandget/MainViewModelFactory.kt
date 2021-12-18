package com.stebakov.setandget

import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory {

    fun createViewModel(activity: MainActivity): MainViewModel {
        return ViewModelProvider(activity)[MainViewModel::class.java]
    }
}