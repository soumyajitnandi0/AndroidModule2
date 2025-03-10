package com.example.fitnesstrackerapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitnesstrackerapp.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Users")
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.myLoginTextView.setOnClickListener {
            startActivity(Intent(this, signup::class.java))
            finish()
        }

        binding.myLoginButton.setOnClickListener {
            val userName = binding.myloginUserName.text.toString()
            val password = binding.myLoginPassword.text.toString()
            if (userName.isNotEmpty() && password.isNotEmpty()) {
                loginUser(userName, password)
            } else {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(userName: String, password: String) {
        databaseReference.orderByChild("userName").equalTo(userName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    if (datasnapshot.exists()) {
                        var isPasswordCorrect = false
                        for (userSnapshot in datasnapshot.children) {
                            val userData = userSnapshot.getValue(userdata::class.java)
                            if (userData != null && userData.password == password) {
                                isPasswordCorrect = true

                                val editor = sharedPreferences.edit()
                                editor.putBoolean("isLoggedIn", true)
                                editor.putString("userId", userData.id)
                                editor.putString("userName", userData.userName)
                                editor.apply()

                                Toast.makeText(this@login, "Login Successfully", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@login, MainActivity::class.java))
                                finish()
                                break
                            }
                        }
                        if (!isPasswordCorrect) {
                            Toast.makeText(this@login, "Incorrect Password", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@login, "User Not Found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@login, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}