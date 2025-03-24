package com.example.authusingroomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val name: String?=null,
    val password:String?=null,
)
