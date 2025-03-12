package com.example.announcementapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class announcementadapter(private val announcementList: MutableList<announcementdata>):RecyclerView.Adapter<announcementviewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): announcementviewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.announcement_layout,parent,false)
        return announcementviewholder(view)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    override fun onBindViewHolder(holder: announcementviewholder, position: Int) {
        holder.bind(announcementList[position])
    }
}