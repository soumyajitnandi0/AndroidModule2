package com.example.authusingroomdb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.authusingroomdb.databinding.ActivitySignupBinding
import kotlinx.coroutines.launch

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user.db"
        ).build()

        binding.mySignupButton.setOnClickListener {
            val username = binding.mySignupUsername.text.toString()
            val password = binding.mySignupPassword.text.toString()

            lifecycleScope.launch {
                signupUser(username, password)
            }
        }

        binding.mySignupTextView.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private suspend fun signupUser(username: String, password: String) {
        val allUserList: List<UserData> = database.userDao().getAllUsers()
        val exists = allUserList.any { it.name == username }

        if (exists) {
            Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show()
        } else {
            val user = UserData(name = username, password = password)
            database.userDao().insertUser(user)
            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}
