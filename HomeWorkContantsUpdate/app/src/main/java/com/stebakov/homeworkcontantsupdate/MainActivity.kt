package com.stebakov.homeworkcontantsupdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), SaveChangeListener, UserClickedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        when (resources.getBoolean(R.bool.isTablet)) {
            false -> {
                supportFragmentManager.beginTransaction().run {
                    val contactsListFragment = ListUsersFragment.newInstance(null, null, null, null)
                    replace(R.id.fragmentContainerView, contactsListFragment)
                    commit()
                }
            }
            true -> {
                supportFragmentManager.beginTransaction().run {
                    val contactsListFragment = ListUsersFragment.newInstance(null, null, null, null)
                    replace(R.id.contactsListFragmentContainerView, contactsListFragment)
                    commit()
                }
            }
        }
    }

    override fun contactClicked(
        contactId: Int,
        contactName: String,
        contactSurname: String,
        contactPhone: String,
        contactPhoto: String
    ) {
        when (resources.getBoolean(R.bool.isTablet)) {
            false -> {
                supportFragmentManager.beginTransaction().run {
                    val contactDetailsFragment = DetailUserFragment.newInstance(
                        contactId,
                        contactName,
                        contactSurname,
                        contactPhone,
                        contactPhoto
                    )
                    replace(R.id.fragmentContainerView, contactDetailsFragment)
                    addToBackStack(null)
                    commit()
                }
            }
            true -> {
                supportFragmentManager.beginTransaction().run {
                    val contactDetailsFragment = DetailUserFragment.newInstance(
                        contactId,
                        contactName,
                        contactSurname,
                        contactPhone,
                        contactPhoto
                    )
                    replace(R.id.contactDetailsFragmentContainerView, contactDetailsFragment)
                    commit()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!resources.getBoolean(R.bool.isTablet)) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun saveChangesButtonClicked(
        contactId: Int,
        contactName: String,
        contactSurname: String,
        contactPhone: String
    ) {
        when (resources.getBoolean(R.bool.isTablet)) {
            false -> {
                supportFragmentManager.beginTransaction().run {
                    val contactsListFragment = ListUsersFragment.newInstance(
                        contactId,
                        contactName,
                        contactSurname,
                        contactPhone
                    )
                    replace(R.id.fragmentContainerView, contactsListFragment)
                    commit()
                }
            }
            true -> {
                supportFragmentManager.beginTransaction().run {
                    val contactsListFragment = ListUsersFragment.newInstance(
                        contactId,
                        contactName,
                        contactSurname,
                        contactPhone
                    )
                    replace(R.id.contactsListFragmentContainerView, contactsListFragment)
                    commit()
                }
            }
        }
    }
}