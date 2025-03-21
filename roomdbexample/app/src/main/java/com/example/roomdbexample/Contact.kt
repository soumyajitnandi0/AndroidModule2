package com.example.roomdbexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id:Long?=null,
    val name:String,
    val phone:String
)
