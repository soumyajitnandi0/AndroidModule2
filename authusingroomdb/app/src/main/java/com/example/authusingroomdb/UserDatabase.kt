package com.example.authusingroomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserData::class], version = 1)
abstract class UserDatabase: RoomDatabase()  {
    abstract fun userDao(): UserDAO
}