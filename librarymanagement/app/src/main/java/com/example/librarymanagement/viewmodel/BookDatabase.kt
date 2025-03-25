package com.example.librarymanagement.viewmodel

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.librarymanagement.model.BookData

@Database(entities = [BookData::class], version = 1)
abstract class BookDatabase:RoomDatabase() {
    abstract fun bookDao(): BookDAO
}