package com.example.noteappsqlite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteappsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: rvadapter
    private lateinit var databaseHelper: databasehelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = databasehelper(this)

        loadNotes()

        binding.addnotebtn.setOnClickListener {
            startActivity(Intent(this, input::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        val noteList = databaseHelper.getNotes()
        adapter = rvadapter(noteList)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)
    }
}
