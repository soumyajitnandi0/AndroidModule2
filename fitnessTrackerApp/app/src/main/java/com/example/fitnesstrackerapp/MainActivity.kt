package com.example.fitnesstrackerapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstrackerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var workoutAdapter: workout_adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        recyclerView = binding.recyclerView
        val workoutList = listOf(
            workout_data("workout1", "30mins"),
            workout_data("workout2", "45mins"),
            workout_data("workout3", "60mins"),
            workout_data("workout4", "30mins"),
            workout_data("workout5", "45mins"),
            workout_data("workout6", "60mins"),
            workout_data("workout7", "30mins"),
            workout_data("workout8", "45mins"),
            workout_data("workout9", "60mins"),
            workout_data("workout10", "30mins"),
            workout_data("workout11", "45mins"),
            workout_data("workout12", "60mins"),
            workout_data("workout13", "30mins"),
            workout_data("workout14", "45mins"),
            workout_data("workout15", "60mins")
        )

        workoutAdapter = workout_adapter(workoutList)
        recyclerView.adapter = workoutAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.button.setOnClickListener {
            val intent = Intent(this, getstartedactivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            startActivity(Intent(this@MainActivity, login::class.java))
            finish()
        }
    }
}