package com.example.sharedpref

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedpref.databinding.ActivityMainBinding
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var shareedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shareedPreferences = getSharedPreferences("Notes", Context.MODE_PRIVATE)
        binding.mySubmitTextButton.setOnClickListener {
            val note = binding.myEditText.text.toString()
            val editor = shareedPreferences.edit()
            editor.putString("key", note)
            editor.apply()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
            binding.myEditText.text.clear()
        }

        binding.myViewTextButton.setOnClickListener {
            val storedData = shareedPreferences.getString("key", "no data")
            binding.myTextView.text = "$storedData"
        }
    }
}