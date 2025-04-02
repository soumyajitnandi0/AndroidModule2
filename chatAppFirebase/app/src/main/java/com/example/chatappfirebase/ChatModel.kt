package com.example.chatappfirebase

data class ChatModel(
    val messageId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = ""
)
