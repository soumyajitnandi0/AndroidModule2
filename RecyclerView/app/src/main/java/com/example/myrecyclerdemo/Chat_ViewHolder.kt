package com.example.myrecyclerdemo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Chat_ViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView){

    private val groupName : TextView = itemView.findViewById(R.id.MyGroupName)
    private val groupMessage : TextView = itemView.findViewById(R.id.MyGroupMessage)
    private val timeStamp : TextView = itemView.findViewById(R.id.MyTimeStamp)
    private val profileIMage : ImageView = itemView.findViewById(R.id.myImageView)

    fun bind(chatItem:Chat_Item){
        groupName.text = chatItem.groupName
        groupMessage.text = chatItem.groupMessage
        timeStamp.text = chatItem.timeStamp
        profileIMage.setImageResource(chatItem.image)
    }
}