package com.example.tabdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class myadapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private final val nTabs :Int = 4
    override fun getItemCount(): Int {
        return nTabs;
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0->return chats()
            1->return updates()
            2->return calls()
            3->return profile()
            else->return chats()
        }
    }
}