package com.example.librarymanagement.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.librarymanagement.R
import com.example.librarymanagement.databinding.ActivityAddBookBinding
import com.example.librarymanagement.model.BookData
import com.example.librarymanagement.viewmodel.BookDatabase
import kotlinx.coroutines.launch

class AddBook : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private lateinit var db: BookDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java,
            "book.db"
        ).build()

        binding.saveBookBtn.setOnClickListener{
            val title = binding.addBookName.text.toString()
            val author = binding.addBookAuthor.text.toString()
            val genre = binding.addBookGenre.text.toString()
            val description = binding.addBookDescription.text.toString()

            lifecycleScope.launch {
                addBook(title, author, genre, description)
            }
        }
    }
    suspend fun addBook(title:String, author: String, genre: String, description: String){
        val book = BookData(bookTitle = title, bookAuthor = author, bookGenre = genre, bookDescription = description)
        db.bookDao().insertBook(book)
    }
}