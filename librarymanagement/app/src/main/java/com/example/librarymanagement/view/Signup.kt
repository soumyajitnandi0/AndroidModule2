package com.example.librarymanagement.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.librarymanagement.R
import com.example.librarymanagement.databinding.ActivitySignupBinding
import com.example.librarymanagement.model.UserData
import com.example.librarymanagement.viewmodel.UserDatabase
import kotlinx.coroutines.launch

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var db: UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user2.db"
        ).build()

        binding.mySignupButton.setOnClickListener {
            val username = binding.mySignupUsername.text.toString()
            val password = binding.mySignupPassword.text.toString()

            lifecycleScope.launch {
                signupUser(username, password)
            }
        }

        binding.mySignupTextView.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private suspend fun signupUser(username: String, password: String) {
        val allUserList: List<UserData> = db.userDao().getAllUsers()
        val exists = allUserList.any { it.userName == username }

        if (exists) {
            Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show()
        } else {
            val user = UserData(userName = username, password = password)
            db.userDao().insertUser(user)
            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}