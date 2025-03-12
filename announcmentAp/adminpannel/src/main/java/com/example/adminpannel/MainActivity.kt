package com.example.adminpannel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminpannel.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val title = binding.title.text.toString()
            val announcement = binding.announcement.text.toString()
            databseReference = FirebaseDatabase.getInstance().getReference("Announcement")
            databseReference.child(title).setValue(announcement).addOnSuccessListener {
                binding.title.text.clear()
                binding.announcement.text.clear()
                Toast.makeText(this, "Announcement Posted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}