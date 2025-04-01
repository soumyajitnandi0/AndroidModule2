package com.example.exampleretrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

class AlbumAdapter(private val albumList: Albums?) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAlbumTitle: TextView = view.findViewById(R.id.tvAlbumTitle)
        val tvAlbumId: TextView = view.findViewById(R.id.tvAlbumId)
        val tvUserId: TextView = view.findViewById(R.id.tvUserId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList?.get(position)
        if (album != null) {
            holder.tvAlbumTitle.text = "Title: ${album.title}"
        }
        if (album != null) {
            holder.tvAlbumId.text = "Album ID: ${album.id}"
        }
        if (album != null) {
            holder.tvUserId.text = "User ID: ${album.userId}"
        }
    }

    override fun getItemCount(): Int {
        return albumList?.size ?: 0
    }
}
