package com.example.librarymanagement.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.R
import com.example.librarymanagement.model.BookData

class BookRvAdapter(
    private val bookList: List<BookData>): RecyclerView.Adapter<BookRvAdapter.BookViewHolder>() {

        inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val bookTitle = itemView.findViewById<TextView>(R.id.bookTitle)
            val bookAuthor = itemView.findViewById<TextView>(R.id.bookAuthor)
            val bookGenre = itemView.findViewById<TextView>(R.id.bookGenre)
            val bookDescription = itemView.findViewById<TextView>(R.id.bookDescription)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bookrv_layout, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bookTitle.text = bookList[position].bookTitle
        holder.bookAuthor.text = bookList[position].bookAuthor
        holder.bookGenre.text = bookList[position].bookGenre
        holder.bookDescription.text = bookList[position].bookDescription
    }
}