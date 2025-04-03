package com.example.firebasenoteapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenoteapp.R
import com.example.firebasenoteapp.model.NotesData

class NotesAdapter(private val notesList: ArrayList<NotesData>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleinput)
        val content: TextView = itemView.findViewById(R.id.noteinput)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notelayout, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]
        holder.title.text = note.title
        holder.content.text = note.content
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}
