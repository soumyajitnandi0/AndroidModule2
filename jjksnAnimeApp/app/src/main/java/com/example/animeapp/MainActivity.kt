package com.example.animeapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val characterFragment = character_fragment()
        val clipsFragment = clips_fragment()
        val infoFragment = info_fragment()

        val charBtn = findViewById<LinearLayout>(R.id.characters)
        val clipsBtn = findViewById<LinearLayout>(R.id.clips)
        val infoBtn = findViewById<LinearLayout>(R.id.info)

        setCurrentFragment(characterFragment)

        charBtn.setOnClickListener {
            setCurrentFragment(characterFragment)
        }

        infoBtn.setOnClickListener {
            setCurrentFragment(infoFragment)
        }

        clipsBtn.setOnClickListener {
            setCurrentFragment(clipsFragment)
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
}