package com.example.crudappadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudappadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.Reference

class uploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            val uid = binding.uid.text.toString()
            val name = binding.name.text.toString()
            val course = binding.course.text.toString()
            val mentor = binding.mentor.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Students")
            val students = StudentData(uid, name, course, mentor)
            databaseReference.child(uid).setValue(students).addOnSuccessListener {
                binding.uid.text.clear()
                binding.name.text.clear()
                binding.course.text.clear()
                binding.mentor.text.clear()
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}