package com.stebakov.homeworkcontantsupdate

interface SaveChangeListener {
    fun saveChangesButtonClicked(contactId: Int, contactName: String, contactSurname: String, contactPhone: String)
}