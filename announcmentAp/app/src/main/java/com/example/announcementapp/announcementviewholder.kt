package com.example.announcementapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class announcementviewholder(view: View): RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.title)
    val announcement = view.findViewById<TextView>(R.id.announcement)

    fun bind(data: announcementdata){
        title.text = data.title
        announcement.text = data.announcement
    }
}