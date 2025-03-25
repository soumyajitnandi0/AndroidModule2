package com.example.librarymanagement.viewmodel

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.librarymanagement.model.UserData

@Database(entities = [UserData::class], version = 1)
abstract class UserDatabase :RoomDatabase(){
    abstract fun userDao(): UserDAO
}