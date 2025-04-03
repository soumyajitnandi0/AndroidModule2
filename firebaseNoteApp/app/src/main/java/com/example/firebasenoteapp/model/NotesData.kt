package com.example.firebasenoteapp.model

data class NotesData(
    val noteId: String? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
