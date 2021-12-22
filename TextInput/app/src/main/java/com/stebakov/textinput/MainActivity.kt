package com.stebakov.textinput

import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val textInputEditText = textInputLayout.editText as TextInputEditText
        val loginButton = findViewById<Button>(R.id.loginButton)
        

        loginButton.setOnClickListener{
            if (EMAIL_ADDRESS.matcher(textInputEditText.text.toString()).matches()){
                hideKeyBoard(textInputEditText)
                loginButton.isEnabled = false
                Snackbar.make(loginButton, "Go to postLogin", Snackbar.LENGTH_LONG).show()
            } else {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = getString(R.string.invalid_email_message)
            }
        }

        textInputEditText.listenChanges{textInputLayout.isErrorEnabled = false}

//        textInputEditText.addTextChangedListener(object : SimpleTextWatcher() {
//            override fun afterTextChanged(p0: Editable?) {
//                val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()
//                textInputLayout.isErrorEnabled = !valid
//                val error = if (valid) "" else getString(R.string.invalid_email_message)
//                textInputLayout.error = error
//                if (valid)
//                    Toast.makeText(
//                    this@MainActivity,
//                    R.string.valid_email_message,
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })

    }
}

private fun TextInputEditText.listenChanges(block : (text : String) -> Unit){
    addTextChangedListener(object : SimpleTextWatcher(){
        override fun afterTextChanged(p0: Editable?) {
            block.invoke(p0.toString())
        }
    })
}

fun AppCompatActivity.hideKeyBoard(view : View){
    val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken,0)
}
