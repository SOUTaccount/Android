package com.stebakov.homeworkcontantsupdate

interface UserClickedListener {
    fun contactClicked(contactId: Int, contactName: String, contactSurname: String, contactPhone: String, contactPhoto: String)
}