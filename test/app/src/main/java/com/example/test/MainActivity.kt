package com.example.test

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent
import com.example.test.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons inside onCreate
        val yBtn = findViewById<Button>(R.id.y)
        val uBtn = findViewById<Button>(R.id.u)
        val gBtn = findViewById<Button>(R.id.g)

        // Set click listeners inside onCreate
        yBtn.setOnClickListener {
            showY()
        }

        uBtn.setOnClickListener {
            showU()
        }

        gBtn.setOnClickListener {
            showG()
        }
    }

    // Functions to show activities
    private fun showY() {
        val intent = Intent(this, y::class.java)
        startActivity(intent)
        finish()
    }

    private fun showU() {
        val intent = Intent(this, u::class.java)
        startActivity(intent)
        finish()
    }

    private fun showG() {
        val intent = Intent(this, g::class.java)
        startActivity(intent)
        finish()
    }
}