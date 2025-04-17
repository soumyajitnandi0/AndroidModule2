package com.example.chatappfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val chatList: List<ChatModel>, private val currentUserId: String) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val layoutLeft: LinearLayout = itemView.findViewById(R.id.layoutLeft)
        private val layoutRight: LinearLayout = itemView.findViewById(R.id.layoutRight)
        private val tvSenderMessage: TextView = itemView.findViewById(R.id.tvMessageRight)
        private val tvReceiverMessage: TextView = itemView.findViewById(R.id.tvMessageLeft)
        private val tvTimeLeft: TextView = itemView.findViewById(R.id.tvTimeLeft)
        private val tvTimeRight: TextView = itemView.findViewById(R.id.tvTimeRight)

        fun bind(chat: ChatModel) {
            // Format current time for demo - in a real app, you'd store timestamps with messages
            val time = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())

            if (chat.senderId == currentUserId) {
                // This is an outgoing message
                layoutRight.visibility = View.VISIBLE
                layoutLeft.visibility = View.GONE
                tvSenderMessage.text = chat.message
                tvTimeRight.text = time
            } else {
                // This is an incoming message
                layoutLeft.visibility = View.VISIBLE
                layoutRight.visibility = View.GONE
                tvReceiverMessage.text = chat.message
                tvTimeLeft.text = time
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