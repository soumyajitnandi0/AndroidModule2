package com.example.firebasenoteapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasenoteapp.databinding.ActivityAddNotesBinding
import com.example.firebasenoteapp.model.NotesData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNotes : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId") ?: return

        databaseReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId).child("notes")

        binding.addbtn.setOnClickListener {
            val title = binding.titleinput.text.toString().trim()
            val content = binding.noteinput.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                saveNote(title, content)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveNote(title: String, content: String) {
        val noteId = databaseReference.push().key ?: return
        val note = NotesData(noteId, title, content)

        databaseReference.child(noteId).setValue(note).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
