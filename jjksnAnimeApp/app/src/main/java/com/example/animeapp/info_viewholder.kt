package com.example.animeapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class info_viewholder(viewholder: View): RecyclerView.ViewHolder(viewholder) {
    private val info_name = viewholder.findViewById<TextView>(R.id.info_name)
    private val info_des = viewholder.findViewById<TextView>(R.id.des)
    private val info_image = viewholder.findViewById<ImageView>(R.id.info_img)

    fun bind(info_data: info_data){
        info_name.text = info_data.name
        info_des.text = info_data.des
        info_image.setImageResource(info_data.image)
    }
}