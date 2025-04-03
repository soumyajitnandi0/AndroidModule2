package com.example.firebasenoteapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasenoteapp.databinding.ActivityMainBinding
import com.example.firebasenoteapp.model.NotesData
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesList: ArrayList<NotesData>
    private lateinit var userId: String
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null) ?: run {
            startActivity(Intent(this, Login::class.java))
            finish()
            return
        }

        notesList = ArrayList()
        notesAdapter = NotesAdapter(notesList)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = notesAdapter

        databaseReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId).child("notes")

        loadNotes()

        binding.addbtn.setOnClickListener {
            val intent = Intent(this, AddNotes::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun loadNotes() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notesList.clear()
                for (noteSnapshot in snapshot.children) {
                    val note = noteSnapshot.getValue(NotesData::class.java)
                    note?.let { notesList.add(it) }
                }
                notesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed to load notes", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}
