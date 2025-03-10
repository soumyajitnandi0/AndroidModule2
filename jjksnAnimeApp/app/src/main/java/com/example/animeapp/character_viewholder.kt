package com.example.animeapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class character_viewholder(viewholder:View):RecyclerView.ViewHolder(viewholder) {

    private val img = viewholder.findViewById<ImageView>(R.id.img)
    private val name = viewholder.findViewById<TextView>(R.id.name)

    fun bind(character:character_data){
        img.setImageResource(character.image)
        name.text = character.name
    }
}