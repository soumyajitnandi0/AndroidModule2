package com.example.librarymanagement.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.librarymanagement.databinding.ActivityChangePasswordBinding
import com.example.librarymanagement.viewmodel.UserDatabase
import kotlinx.coroutines.launch

class Change_Password : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user2.db"
        ).build()

        binding.changePasswordBtn.setOnClickListener {
            val newPassword = binding.newPassword.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (newPassword == confirmPassword) {
                lifecycleScope.launch {
                    changePassword(username, newPassword)
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun changePassword(username: String?, newPassword: String) {
        if (username != null) {
            db.userDao().changePassword(username, newPassword)
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}