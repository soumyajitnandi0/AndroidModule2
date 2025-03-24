package com.example.kalkulatorappdemo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kalkulatorappdemo.R
import com.example.kalkulatorappdemo.databinding.ActivityMainBinding
import com.example.kalkulatorappdemo.viewmodel.MyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        binding.output.text = "${viewModel.result}"

        binding.addBtn.setOnClickListener {
            val n1 = binding.n1.text.toString().toInt()
            val n2 = binding.n2.text.toString().toInt()
            val res = viewModel.add(n1, n2)
            binding.output.text = "${res.result}"
        }
    }
}