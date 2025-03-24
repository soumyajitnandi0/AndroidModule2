package com.example.tabkalkulator.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tabkalkulator.R
import com.example.tabkalkulator.databinding.ActivityMainBinding
import com.example.tabkalkulator.viewmodel.Adapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = Adapter(this)
        binding.viewpager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when(position){
                0 -> tab.text = "Add"
                1 -> tab.text = "Sub"
                2 -> tab.text = "Mul"
                3 -> tab.text = "Div"
            }
        }.attach()
    }
}