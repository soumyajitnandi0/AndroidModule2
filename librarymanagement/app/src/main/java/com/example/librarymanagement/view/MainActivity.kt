// app/src/main/java/com/example/librarymanagement/MainActivity.kt
package com.example.librarymanagement.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.librarymanagement.databinding.ActivityMainBinding
import com.example.librarymanagement.model.BookData
import com.example.librarymanagement.viewmodel.BookDatabase
import com.example.librarymanagement.viewmodel.BookRvAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: BookDatabase
    private lateinit var bookList: List<BookData>
    private lateinit var bookAdapter: BookRvAdapter
    private lateinit var reciever: Airplanemode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        db = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java,
            "book.db"
        ).build()

        reciever = Airplanemode()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(reciever, it)
        }

        lifecycleScope.launch {
            getAllBooks()
            bookAdapter = BookRvAdapter(bookList)
            binding.rv.adapter = bookAdapter
            binding.rv.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.profileBtn.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        binding.addBookBtn.setOnClickListener {
            startActivity(Intent(this, AddBook::class.java))
        }
    }

    private suspend fun getAllBooks() {
        bookList = db.bookDao().getAllBooks()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reciever)
    }
}