package com.example.noteappsqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.noteappsqlite.databinding.ActivityInputBinding

class input : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private lateinit var databaseHelper: databasehelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = databasehelper(this)
        binding.addbtn.setOnClickListener {
            Toast.makeText(this,"Kyu Likh Li Prem Katha Apni",Toast.LENGTH_LONG).show()
            val titleInput = binding.titleinput.text.toString().trim()
            val noteInput = binding.noteinput.text.toString().trim()

            if (titleInput.isEmpty() || noteInput.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (databaseHelper.addNote(titleInput, noteInput)) {
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.backbtn.setOnClickListener {
            Toast.makeText(this,"Chal Nikal",Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
