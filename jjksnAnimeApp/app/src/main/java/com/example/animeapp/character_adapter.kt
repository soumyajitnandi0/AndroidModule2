package com.example.animeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class character_adapter(private val character_list:List<character_data>): RecyclerView.Adapter<character_viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): character_viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.character_fragment_layout,parent,false)
        return character_viewholder(view)
    }

    override fun getItemCount(): Int {
        return character_list.size
    }

    override fun onBindViewHolder(holder: character_viewholder, position: Int) {
        holder.bind(character_list[position])
    }
}