package com.example.fitnesstrackerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class workout_adapter(private val workouts: List<workout_data>) : RecyclerView.Adapter<workout_viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): workout_viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.log_layout, parent, false)
        return workout_viewholder(view)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: workout_viewholder, position: Int) {
        holder.bind(workouts[position])
    }
}