package com.example.authusingroomdb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.authusingroomdb.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user.db"
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
        val databyname = database.userDao().getUserByName(username)
        if (databyname != null) {
            if (databyname.password == password) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show()
        }
    }
}
