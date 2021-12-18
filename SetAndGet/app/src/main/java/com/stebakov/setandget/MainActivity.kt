package com.stebakov.setandget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = MainViewModelFactory().createViewModel(this)
        val textView = findViewById<TextView>(R.id.textView)
        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        val buttonSave = findViewById<Button>(R.id.button2)
        val buttonLoad = findViewById<Button>(R.id.button)

        vm.result.observe(this,{
            textView.text = it
        })

        buttonSave.setOnClickListener(View.OnClickListener { vm.save(editText.text.toString()) })
        buttonLoad.setOnClickListener(View.OnClickListener { vm.load() })
    }
}