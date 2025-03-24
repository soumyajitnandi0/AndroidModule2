package com.example.tabkalkulator.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tabkalkulator.R
import com.example.tabkalkulator.databinding.AddBinding
import com.example.tabkalkulator.databinding.SubBinding
import com.example.tabkalkulator.viewmodel.MyViewModel

class Sub: Fragment(R.layout.sub){
    private lateinit var binding: SubBinding
    private lateinit var myViewModel: MyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SubBinding.bind(view)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.divBtn.setOnClickListener {
            val n1 = binding.n1.text.toString().toFloat()
            val n2 = binding.n2.text.toString().toFloat()
            val result = myViewModel.sub(n1, n2)
            binding.output.text = result.result
        }
    }
}