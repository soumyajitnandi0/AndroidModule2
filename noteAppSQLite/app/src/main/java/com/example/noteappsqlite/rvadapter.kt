package com.example.noteappsqlite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappsqlite.databinding.NotervLayoutBinding

class rvadapter(private val noteList: List<notedata>) : RecyclerView.Adapter<rvadapter.ViewHolder>() {

    class ViewHolder(binding: NotervLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleValue = binding.titleinput
        val noteValue = binding.noteinput
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NotervLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = noteList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.titleValue.text = note.title
        holder.noteValue.text = note.note
    }
}
