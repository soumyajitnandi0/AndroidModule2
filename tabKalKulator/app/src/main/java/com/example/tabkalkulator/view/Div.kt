package com.example.tabkalkulator.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tabkalkulator.R
import com.example.tabkalkulator.databinding.DivBinding
import com.example.tabkalkulator.viewmodel.MyViewModel

class Div: Fragment(R.layout.div) {
    private lateinit var binding: DivBinding
    private lateinit var myViewModel: MyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DivBinding.bind(view)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.divBtn.setOnClickListener {
            val n1 = binding.n1.text.toString().toFloat()
            val n2 = binding.n2.text.toString().toFloat()
            val result = myViewModel.divide(n1, n2)
            binding.output.text = result.result
        }
    }
}