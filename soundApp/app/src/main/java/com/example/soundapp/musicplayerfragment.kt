package com.example.soundapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class musicplayerfragment:Fragment() {
    companion object {
        fun newInstance(title: String, artist: String): musicplayerfragment {
            val fragment = musicplayerfragment()
            val args = Bundle().apply {
                putString("SONG_TITLE", title)
                putString("SONG_ARTIST", artist)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.music_player_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("SONG_TITLE")
        val artist = arguments?.getString("SONG_ARTIST")

        view.findViewById<TextView>(R.id.songTitle).text = title
        view.findViewById<TextView>(R.id.songArtist).text = artist

        val playPauseBtn = view.findViewById<ImageButton>(R.id.playbtn)
        var isPlaying = false

        view.findViewById<ImageButton>(R.id.closebtn).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        playPauseBtn.setOnClickListener {
            val context = view.context
            val songResId = context.resources.getIdentifier(
                title?.lowercase()?.replace(" ", "_"), "raw", context.packageName
            )

            val intent = Intent(context, musicservice::class.java).apply {
                putExtra("SONG_RES_ID", songResId)
                action = if (isPlaying) "PAUSE" else "PLAY"
            }

            context.startService(intent)

            isPlaying = !isPlaying
            playPauseBtn.setImageResource(
                if (isPlaying) R.drawable.pauseicon else R.drawable.ic_play_arrow
            )
        }
    }

}