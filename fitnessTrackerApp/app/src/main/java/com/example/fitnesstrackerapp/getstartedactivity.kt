package com.example.fitnesstrackerapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.fitnesstrackerapp.databinding.ActivityGetstartedactivityBinding

class getstartedactivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetstartedactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetstartedactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutfragment = workouts_fragment()
        val progressFragment = progress_fragment()
        val goalsFragment = goals_fragment()

        setCurrentFragment(workoutfragment)
        binding.workouts.setOnClickListener {
            setCurrentFragment(workoutfragment)
        }
        binding.progress.setOnClickListener {
            setCurrentFragment(progressFragment)
        }
        binding.goals.setOnClickListener {
            setCurrentFragment(goalsFragment)
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
}