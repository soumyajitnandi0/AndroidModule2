package com.example.animeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class info_adapter(val info_list:List<info_data>): RecyclerView.Adapter<info_viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): info_viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.info_fragment_layout,parent,false)
        return info_viewholder(view)
    }

    override fun getItemCount(): Int {
        return info_list.size
    }

    override fun onBindViewHolder(holder: info_viewholder, position: Int) {
        holder.bind(info_list[position])
    }
}