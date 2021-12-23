package com.stebakov.timermvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)
        val observable = TextObservable()
        observable.observe(object : TextCallback{
            override fun updateText(str: String) = runOnUiThread {
                textView.text = str
                Log.d("MyLog","updateText at MainActivity, str = $str")
            }
        })
        val viewModel = ViewModel(observable)
        viewModel.init()
    }
}