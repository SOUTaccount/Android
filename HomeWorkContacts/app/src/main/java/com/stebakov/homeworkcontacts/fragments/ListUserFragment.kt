package com.stebakov.homeworkcontacts.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.stebakov.homeworkcontacts.R
import com.stebakov.homeworkcontacts.model.User

private var users = arrayOf(
    User(1, "Vasya", "Vasin", "8880808"),
    User(2, "Petya", "Petin", "62626262"),
    User(3, "Fedya", "Fedin", "4543534534"),
    User(4, "Masha", "Mashin", "858569659")
)

class ListUserFragment : Fragment() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_user, container, false)
        val user1TextView: TextView = view.findViewById(R.id.user1TextView)
        val user2TextView: TextView = view.findViewById(R.id.user2TextView)
        val user3TextView: TextView = view.findViewById(R.id.user3TextView)
        val user4TextView: TextView = view.findViewById(R.id.user4TextView)

        sharedPrefs = requireActivity().getSharedPreferences("NAME", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getInt("KEY_ID",0)
        val name = sharedPrefs.getString("KEY_NAME","")
        val surname = sharedPrefs.getString("KEY_SURNAME","")
        val phone = sharedPrefs.getString("KEY_PHONE","")

        when (userId) {
            1 -> {
                    users[0].name = name!!
                    users[0].surname = surname!!
                    users[0].phone = phone!!
                    user1TextView.text = name
            }
            2 -> {
                    users[1].name = name!!
                    users[1].surname = surname!!
                    users[1].phone = phone!!
                    user2TextView.text = name
            }
            3 -> {
                    users[2].name = name!!
                    users[2].surname = surname!!
                    users[2].phone = phone!!
                    user3TextView.text = name
            }
            4 -> {
                    users[3].name = name!!
                    users[3].surname = surname!!
                    users[3].phone = phone!!
                    user4TextView.text = name
            }
        }

        user1TextView.text = users[0].name
        user2TextView.text = users[1].name
        user3TextView.text = users[2].name
        user4TextView.text = users[3].name

        user1TextView.setOnClickListener {
            sharedPrefs.edit().putInt("KEY_ID",1).apply()
            sharedPrefs.edit().putString("KEY_NAME", users[0].name).apply()
            sharedPrefs.edit().putString("KEY_SURNAME",users[0].surname).apply()
            sharedPrefs.edit().putString("KEY_PHONE",users[0].phone).apply()
            findNavController().navigate(R.id.action_listUserFragment_to_detailUserFragment)
        }

        user2TextView.setOnClickListener {
            sharedPrefs.edit().putInt("KEY_ID",2).apply()
            sharedPrefs.edit().putString("KEY_NAME", users[1].name).apply()
            sharedPrefs.edit().putString("KEY_SURNAME",users[1].surname).apply()
            sharedPrefs.edit().putString("KEY_PHONE",users[1].phone).apply()
            findNavController().navigate(R.id.action_listUserFragment_to_detailUserFragment)
        }

        user3TextView.setOnClickListener {
            sharedPrefs.edit().putInt("KEY_ID",3).apply()
            sharedPrefs.edit().putString("KEY_NAME", users[2].name).apply()
            sharedPrefs.edit().putString("KEY_SURNAME",users[2].surname).apply()
            sharedPrefs.edit().putString("KEY_PHONE",users[2].phone).apply()
            findNavController().navigate(R.id.action_listUserFragment_to_detailUserFragment)
        }

        user4TextView.setOnClickListener {
            sharedPrefs.edit().putInt("KEY_ID",4).apply()
            sharedPrefs.edit().putString("KEY_NAME", users[3].name).apply()
            sharedPrefs.edit().putString("KEY_SURNAME",users[3].surname).apply()
            sharedPrefs.edit().putString("KEY_PHONE",users[3].phone).apply()
            findNavController().navigate(R.id.action_listUserFragment_to_detailUserFragment)
        }
        return view
    }
}