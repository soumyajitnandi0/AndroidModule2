package com.example.librarymanagement.viewmodel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.librarymanagement.model.BookData

@Dao
interface BookDAO {
    @Insert
    suspend fun insertBook(book: BookData)

    @Query("SELECT * FROM BookData")
    suspend fun getAllBooks(): List<BookData>
}