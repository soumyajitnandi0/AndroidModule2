package com.example.services

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var myStartBtn: Button
    private lateinit var myStopBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        myStartBtn = findViewById(R.id.startBtn)
        myStopBtn = findViewById(R.id.stopBtn)

        myStartBtn.setOnClickListener {
            startService(Intent(this@MainActivity, ringtoneservices::class.java))
        }
        myStopBtn.setOnClickListener {
            stopService(Intent(this@MainActivity, ringtoneservices::class.java))
        }
    }
}