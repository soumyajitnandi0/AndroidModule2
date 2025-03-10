package com.example.firebaseapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val logOutBtn = findViewById<Button>(R.id.myLogoutButton)
        logOutBtn.setOnClickListener {
            lougoutUser()
        }
    }

    private fun lougoutUser() {
        sharedPreferences.edit().clear().apply()
        startActivity(Intent(this@MainActivity, login::class.java))
        finish()
    }
}