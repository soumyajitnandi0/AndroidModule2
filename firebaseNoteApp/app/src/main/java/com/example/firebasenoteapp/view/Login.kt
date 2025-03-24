package com.example.firebasenoteapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasenoteapp.databinding.ActivityLoginBinding
import com.example.firebasenoteapp.model.UserData
import com.google.firebase.database.*

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val savedUserId = sharedPreferences.getString("userId", null)

        if (savedUserId != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        databaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        binding.myLoginButton.setOnClickListener {
            val username = binding.myloginUserName.text.toString()
            val password = binding.myLoginPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                login(username, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.myLoginTextView.setOnClickListener{
            startActivity(Intent(this, Signup::class.java))
            finish()
        }
    }

    private fun login(username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val userData = userSnapshot.getValue(UserData::class.java)

                            if (userData?.password == password) {
                                Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()

                                val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("userId", userData.id)
                                editor.apply()

                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                return
                            } else {
                                Toast.makeText(this@Login, "Wrong Password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@Login, "User Doesn't Exist", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Login, "Database Error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
