package com.example.fitnesstrackerapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class progress_adapter(fragmentActivity: progress_fragment):FragmentStateAdapter(fragmentActivity) {
    private final val fragments:Int = 2
    override fun getItemCount(): Int {
        return fragments
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0->return weekly()
            1->return monthly()
            else->return weekly()
        }
    }
}