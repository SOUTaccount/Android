package com.stebakov.homeworkcontantsupdate

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView

private const val CONTACT_ID = "contactId"
private const val CONTACT_NAME = "contactName"
private const val CONTACT_SURNAME = "contactSurname"
private const val CONTACT_PHONE = "contactPhone"

private var contacts = ArrayList<User>()

class ListUsersFragment : Fragment(R.layout.fragment_list_users) {
    private val contactId: Int by lazy { requireArguments().getInt(CONTACT_ID, 0) }
    private val contactName: String by lazy { requireArguments().getString(CONTACT_NAME, "") }
    private val contactSurname: String by lazy { requireArguments().getString(CONTACT_SURNAME, "") }
    private val contactPhone: String by lazy { requireArguments().getString(CONTACT_PHONE, "") }
    private lateinit var contactClickedListener: UserClickedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val names = resources.getStringArray(R.array.names)
        val surnames = resources.getStringArray(R.array.surnames)

        for (id in 1..100) {
            contacts.add(
                User(
                    id,
                    names[id - 1],
                    surnames[id - 1],
                    "${id * (500..1000).random()}",
                    "https://picsum.photos/id/$id/200/200"
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val adapter = UsersAdapter { contact ->
            contactClickedListener.contactClicked(contact.id, contact.name, contact.surname, contact.phone, contact.photo)
        }
        recyclerView.adapter = adapter

        if (arguments?.isEmpty == false) {
            val contact = contacts[contactId - 1]
            contact.name = contactName
            contact.surname = contactSurname
            contact.phone = contactPhone
        }

        adapter.setData(contacts)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserClickedListener) {
            contactClickedListener = context
        } else {
            throw ClassCastException("$context must implement ContactClickedListener")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(contactId: Int?, contactName: String?, contactSurname: String?, contactPhone: String?) =
            ListUsersFragment().apply {
                if (contactId != null && contactName != null) {
                    arguments = Bundle().apply {
                        putInt(CONTACT_ID, contactId)
                        putString(CONTACT_NAME, contactName)
                        putString(CONTACT_SURNAME, contactSurname)
                        putString(CONTACT_PHONE, contactPhone)
                    }
                } else {
                    ListUsersFragment()
                }
            }
    }
}