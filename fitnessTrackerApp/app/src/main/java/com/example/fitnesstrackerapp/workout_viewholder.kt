package com.example.fitnesstrackerapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class workout_viewholder(itemview:View): RecyclerView.ViewHolder(itemview){
    val workoutname=itemview.findViewById<TextView>(R.id.workoutname)
    val workoutduration=itemview.findViewById<TextView>(R.id.workoutduration)

    fun bind(workout:workout_data){
        workoutname.text=workout.workout
        workoutduration.text=workout.duration
    }
}