package com.example.soundapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class musicadapter(
    private val songList: List<SongData>,
    private val onSongClick: (SongData, Int) -> Unit
) : RecyclerView.Adapter<musicadapter.MusicViewHolder>() {

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songTitle: TextView = itemView.findViewById(R.id.song_title)
        val songArtist: TextView = itemView.findViewById(R.id.artist_name)
        val albumArt: ImageView = itemView.findViewById(R.id.album_art)
        val playButton: ImageButton = itemView.findViewById(R.id.play_button)
        val musicCard: CardView = itemView.findViewById(R.id.music_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_tile_rvlayout, parent, false)
        return MusicViewHolder(view)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val song = songList[position]
        holder.songTitle.text = song.title
        holder.songArtist.text = song.artist
        holder.albumArt.setImageResource(song.image)

        holder.playButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, musicservice::class.java).apply {
                action = musicservice.ACTION_PLAY
                putParcelableArrayListExtra(musicservice.EXTRA_SONG_LIST, ArrayList(songList))
                putExtra(musicservice.EXTRA_SONG_INDEX, position)
            }
            context.startService(intent)
        }

        holder.musicCard.setOnClickListener {
            onSongClick(song, position)
        }
    }
}