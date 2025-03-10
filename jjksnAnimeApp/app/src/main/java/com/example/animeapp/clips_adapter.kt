package com.example.animeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class clips_adapter(private val clip_list: List<clips_data>) : RecyclerView.Adapter<clips_viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): clips_viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clips_fragment_layout, parent, false)
        return clips_viewholder(view)
    }

    override fun getItemCount(): Int {
        return clip_list.size
    }

    override fun onBindViewHolder(holder: clips_viewholder, position: Int) {
        holder.bind(clip_list[position])
    }

    override fun onViewRecycled(holder: clips_viewholder) {
        super.onViewRecycled(holder)
        holder.releasePlayer()
    }
}