package com.stebakov.timermvvm

import android.util.Log

class ViewModel(private val textObservable: TextObservable) {

    private val model = Model(object :  TextCallback {
        override fun updateText(str: String) {
            textObservable.postValue(str)
            Log.d("MyLog","updateText at ViewModel, str = $str")
        }
    })

    fun init(){
        model.start()
        Log.d("MyLog","MODEL START")
    }

}

class TextObservable {

    private lateinit var callback: TextCallback

    fun observe(callback: TextCallback){
        this.callback = callback
    }

    fun postValue(text: String){
        callback.updateText(text)
    }
}

interface TextCallback {
    fun updateText(str: String)
}