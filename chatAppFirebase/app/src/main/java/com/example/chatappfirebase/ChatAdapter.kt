package com.example.chatappfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val chatList: List<ChatModel>, private val currentUserId: String) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSenderMessage: TextView = itemView.findViewById(R.id.tvMessageRight)
        private val tvReceiverMessage: TextView = itemView.findViewById(R.id.tvMessageLeft)

        fun bind(chat: ChatModel) {
            if (chat.senderId == currentUserId) {
                tvSenderMessage.text = chat.message
                tvSenderMessage.visibility = View.VISIBLE
                tvReceiverMessage.visibility = View.GONE
            } else {
                tvReceiverMessage.text = chat.message
                tvReceiverMessage.visibility = View.VISIBLE
                tvSenderMessage.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount() = chatList.size
}
