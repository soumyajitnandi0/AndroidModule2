// app/src/main/java/com/example/librarymanagement/view/Login.kt
package com.example.librarymanagement.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.librarymanagement.databinding.ActivityLoginBinding
import com.example.librarymanagement.viewmodel.UserDatabase
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user2.db"
        ).build()

        binding.myLoginButton.setOnClickListener {
            val username = binding.myloginUserName.text.toString()
            val password = binding.myLoginPassword.text.toString()

            lifecycleScope.launch {
                loginUser(username, password)
            }
        }

        binding.myLoginTextView.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
            finish()
        }
    }

    private suspend fun loginUser(username: String, password: String) {
        val databyname = db.userDao().getUserByUserName(username)
        if (databyname != null) {
            if (databyname.password == password) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show()
        }
    }
}