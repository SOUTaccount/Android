package com.stebakov.homeworkcontantsupdate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UsersAdapter (private val onClick: (User) -> Unit): RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    private var contactsList = emptyList<User>()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(contact: User) {
            val contactPhotoImageView: ImageView = view.findViewById(R.id.contactPhotoImageView)
            val contactNameTextView: TextView = view.findViewById(R.id.contactNameTextView)

            Picasso.get()
                .load(contact.photo)
                .into(contactPhotoImageView)
            contactNameTextView.text = contact.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactsList[position]
        holder.bind(contact)
        holder.itemView.setOnClickListener { onClick(contact) }
    }

    override fun getItemCount(): Int = contactsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(contactList: List<User>) {
        this.contactsList = contactList
        notifyDataSetChanged()
    }
}