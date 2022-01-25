package com.stebakov.homeworkcontacts.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.stebakov.homeworkcontacts.R


class DetailUserFragment : Fragment() {

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var userEditName: String
    private lateinit var userEditSurname: String
    private lateinit var userEditPhone: String
    private var userId: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_user, container, false)

        sharedPrefs = requireActivity().getSharedPreferences("NAME", Context.MODE_PRIVATE)

        userId = sharedPrefs.getInt("KEY_ID", 0)
        val name = sharedPrefs.getString("KEY_NAME", "")
        val surname = sharedPrefs.getString("KEY_SURNAME", "")
        val phone = sharedPrefs.getString("KEY_PHONE", "")

        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
        val surnameEditText: EditText = view.findViewById(R.id.surnameEditText)
        val phoneEditText: EditText = view.findViewById(R.id.phoneEditText)
        val saveChangesButton: Button = view.findViewById(R.id.saveChangesButton)

        nameEditText.setText(name)
        surnameEditText.setText(surname)
        phoneEditText.setText(phone)


        saveChangesButton.setOnClickListener {
            userEditName = nameEditText.text.toString()
            userEditSurname = surnameEditText.text.toString()
            userEditPhone = phoneEditText.text.toString()
            putData()
            findNavController().navigate(R.id.action_detailUserFragment_to_listUserFragment)
        }

        return view
    }

    private fun putData() {
        sharedPrefs.edit().putInt("KEY_ID", userId!!).apply()
        sharedPrefs.edit().putString("KEY_NAME", userEditName).apply()
        sharedPrefs.edit().putString("KEY_SURNAME", userEditSurname).apply()
        sharedPrefs.edit().putString("KEY_PHONE", userEditPhone).apply()
    }
}