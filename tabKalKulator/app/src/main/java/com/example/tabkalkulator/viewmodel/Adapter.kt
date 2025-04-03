package com.example.tabkalkulator.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tabkalkulator.view.Add
import com.example.tabkalkulator.view.Div
import com.example.tabkalkulator.view.Mul
import com.example.tabkalkulator.view.Sub

class Adapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    private final val tabCount = 4
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return Add()
            1 -> return Sub()
            2 -> return Mul()
            3 -> return Div()
            else -> return Add()
        }
    }

}