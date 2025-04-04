package com.example.chatappfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val users: ArrayList<UserModel>,
    private val onUserClick: (UserModel) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvInitial: TextView = itemView.findViewById(R.id.tvInitial)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.tvUsername.text = user.username

        // Set the initial letter of the username as avatar
        if (user.username.isNotEmpty()) {
            holder.tvInitial.text = user.username[0].toString().uppercase()
        } else {
            holder.tvInitial.text = "?"
        }

        holder.itemView.setOnClickListener { onUserClick(user) }
    }

    override fun getItemCount(): Int = users.size
}