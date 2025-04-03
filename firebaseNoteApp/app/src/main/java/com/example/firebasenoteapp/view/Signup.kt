package com.example.firebasenoteapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasenoteapp.databinding.ActivitySignupBinding
import com.example.firebasenoteapp.model.UserData
import com.google.firebase.database.*

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        binding.mySignupButton.setOnClickListener {
            val username = binding.mySignupUsername.text.toString()
            val password = binding.mySignupPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                signUp(username, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.mySignupTextView.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun signUp(username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        val id = databaseReference.push().key ?: return
                        val userData = UserData(id, username, password)

                        // Save user data
                        databaseReference.child(id).setValue(userData).addOnCompleteListener {
                            Toast.makeText(this@Signup, "Sign Up Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@Signup, Login::class.java))
                            finish()
                        }
                    } else {
                        Toast.makeText(this@Signup, "User already exists", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Signup, "Database Error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
