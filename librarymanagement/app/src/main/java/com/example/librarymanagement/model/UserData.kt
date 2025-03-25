package com.example.librarymanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var userName: String? = null,
    var password: String? = null,
    var email: String? = null,
    var description: String? = null,
)
