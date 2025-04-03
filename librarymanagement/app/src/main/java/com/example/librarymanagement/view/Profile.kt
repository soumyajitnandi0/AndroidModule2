// app/src/main/java/com/example/librarymanagement/view/Profile.kt
package com.example.librarymanagement.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.librarymanagement.databinding.ActivityProfileBinding
import com.example.librarymanagement.viewmodel.UserDatabase
import kotlinx.coroutines.launch

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        binding.name.text = username
        db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user2.db"
        ).build()

        if (username != null) {
            loadUserData(username)
        }

        binding.saveBtn.setOnClickListener {
            val email = binding.addEmail.text.toString()
            val description = binding.addDescription.text.toString()
            addEmailAndDescription(username, email, description)
        }

        binding.chagePasswordBtn.setOnClickListener {
            startActivity(Intent(this, Change_Password::class.java).apply {
                putExtra("username", username)
            })
            finish()
        }

        binding.logoutBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun loadUserData(username: String) {
        lifecycleScope.launch {
            val userData = db.userDao().getUserData(username)
            if (userData.isNotEmpty()) {
                binding.name.text = userData[0].userName
                binding.addEmail.setText(userData[0].email)
                binding.addDescription.setText(userData[0].description)
            }
        }
    }

    private fun addEmailAndDescription(username: String?, email: String, description: String) {
        lifecycleScope.launch {
            if (username != null) {
                db.userDao().updateUserData(username, email, description)
            }
        }
    }
}