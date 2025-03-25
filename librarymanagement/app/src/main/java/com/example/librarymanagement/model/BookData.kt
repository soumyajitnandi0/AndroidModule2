package com.example.librarymanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookData(
    @PrimaryKey(autoGenerate = true)
    var bookId:Int?=null,
    var bookTitle:String?=null,
    var bookAuthor:String?=null,
    var bookGenre:String?=null,
    var bookDescription:String?=null,
)
