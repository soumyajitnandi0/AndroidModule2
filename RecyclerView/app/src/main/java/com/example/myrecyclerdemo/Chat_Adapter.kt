package com.example.myrecyclerdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Chat_Adapter(private val chat_list: List<Chat_Item>): RecyclerView.Adapter<Chat_ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chat_ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return Chat_ViewHolder(view)
    }

    override fun getItemCount() = chat_list.size

    override fun onBindViewHolder(holder: Chat_ViewHolder, position: Int) {
        holder.bind(chat_list[position])
    }
}