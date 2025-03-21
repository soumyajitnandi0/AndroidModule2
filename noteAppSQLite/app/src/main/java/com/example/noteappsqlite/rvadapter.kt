package com.example.noteappsqlite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappsqlite.databinding.NotervLayoutBinding

class rvadapter(private val noteList: List<notedata>) : RecyclerView.Adapter<rvadapter.ViewHolder>() {
    private lateinit var databasehelper: databasehelper
    class ViewHolder(binding: NotervLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleValue = binding.titleinput
        val noteValue = binding.noteinput
        val editbtn = binding.editbtn
        val deletebtn = binding.deletebtn
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
        databasehelper = databasehelper(holder.itemView.context)
        holder.deletebtn.setOnClickListener {
            Toast.makeText(holder.itemView.context,"Hatana Hi Tha To Likha Kyu Tha",Toast.LENGTH_LONG).show()
            if (note.id != null) {
                databasehelper.deleteNote(note.id)

                val updatedList = noteList.toMutableList()
                updatedList.removeAt(position)

                (noteList as MutableList).clear()
                (noteList as MutableList).addAll(updatedList)

                notifyItemRemoved(position)
                notifyItemRangeChanged(position, updatedList.size)
            }
        }
        holder.editbtn.setOnClickListener{
            Toast.makeText(holder.itemView.context, "Pehle Sahi Se Nai Likha Ja Raha Tha", Toast.LENGTH_LONG).show()
        }
    }
}
