package com.example.roomdbexample

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactsDatabase:RoomDatabase(){
    abstract fun contactDao(): ContactsDAO
}