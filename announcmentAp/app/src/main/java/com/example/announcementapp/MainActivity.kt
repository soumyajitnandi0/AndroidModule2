package com.example.announcementapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.announcementapp.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var adapter: announcementadapter
    private lateinit var announcementList: MutableList<announcementdata>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Announcement")

        // Setup RecyclerView and Adapter
        announcementList = mutableListOf()
        adapter = announcementadapter(announcementList)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        // Fetch data from Firebase
        fetchData()
    }

    private fun fetchData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the old list to avoid duplicates
                announcementList.clear()

                // Loop through each child in the snapshot
                for (data in snapshot.children) {
                    val title = data.key ?: "No Title"
                    val content = data.value?.toString() ?: "No Content"
                    Log.d("FirebaseData", "Title: $title, Content: $content")

                    // Add each announcement to the list
                    announcementList.add(announcementdata(title, content))
                }

                // Log the total count to confirm all data is fetched
                Log.d("FirebaseData", "Total announcements loaded: ${announcementList.size}")

                // Notify adapter that data has changed
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error loading announcements!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
