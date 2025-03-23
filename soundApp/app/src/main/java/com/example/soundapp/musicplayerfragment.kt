package com.example.soundapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class musicplayerfragment : Fragment() {
    private var musicService: musicservice? = null
    private var serviceBound = false
    private var currentSongTitle: String? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as musicservice.MusicBinder
            musicService = binder.getService()
            serviceBound = true
            updateUI()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            serviceBound = false
        }
    }

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

        currentSongTitle = arguments?.getString("SONG_TITLE")
        val artist = arguments?.getString("SONG_ARTIST")

        view.findViewById<TextView>(R.id.songTitle).text = currentSongTitle
        view.findViewById<TextView>(R.id.songArtist).text = artist

        // Bind to the music service
        val intent = Intent(context, musicservice::class.java)
        context?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        setupButtonListeners(view)
    }

    private fun setupButtonListeners(view: View) {
        // Close button
        view.findViewById<ImageButton>(R.id.closebtn).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Play/Pause button
        view.findViewById<ImageButton>(R.id.playbtn).setOnClickListener {
            if (musicService?.isPlaying() == true) {
                context?.startService(Intent(context, musicservice::class.java).apply {
                    action = musicservice.ACTION_PAUSE
                })
            } else {
                context?.startService(Intent(context, musicservice::class.java).apply {
                    action = musicservice.ACTION_PLAY
                })
            }
            updateUI()
        }

        // Previous button
        view.findViewById<ImageButton>(R.id.prevBtn).setOnClickListener {
            context?.startService(Intent(context, musicservice::class.java).apply {
                action = musicservice.ACTION_PREVIOUS
            })
            // We need to update UI after a delay to let the service switch songs
            view.postDelayed({ updateUI() }, 300)
        }

        // Next button
        view.findViewById<ImageButton>(R.id.nextBtn).setOnClickListener {
            context?.startService(Intent(context, musicservice::class.java).apply {
                action = musicservice.ACTION_NEXT
            })
            // We need to update UI after a delay to let the service switch songs
            view.postDelayed({ updateUI() }, 300)
        }

        // Favorite button
        view.findViewById<ImageButton>(R.id.favoriteBtn).setOnClickListener {
            safeAction {
                context?.startService(Intent(context, musicservice::class.java).apply {
                    action = musicservice.ACTION_TOGGLE_FAVORITE
                })
                updateUI()
            }
        }

        // Repeat button
        view.findViewById<ImageButton>(R.id.repeatBtn).setOnClickListener {
            safeAction {
                context?.startService(Intent(context, musicservice::class.java).apply {
                    action = musicservice.ACTION_TOGGLE_REPEAT
                })
                updateUI()
            }
        }

        // Shuffle button
        view.findViewById<ImageButton>(R.id.shuffleBtn).setOnClickListener {
            safeAction {
                context?.startService(Intent(context, musicservice::class.java).apply {
                    action = musicservice.ACTION_TOGGLE_SHUFFLE
                })
                updateUI()
            }
        }
    }

    private fun updateUI() {
        view?.let { view ->
            val playPauseBtn = view.findViewById<ImageButton>(R.id.playbtn)
            val favoriteBtn = view.findViewById<ImageButton>(R.id.favoriteBtn)
            val repeatBtn = view.findViewById<ImageButton>(R.id.repeatBtn)
            val shuffleBtn = view.findViewById<ImageButton>(R.id.shuffleBtn)
            val titleView = view.findViewById<TextView>(R.id.songTitle)
            val artistView = view.findViewById<TextView>(R.id.songArtist)

            musicService?.let { service ->
                // Update play/pause button icon
                playPauseBtn.setImageResource(
                    if (service.isPlaying()) R.drawable.pauseicon else R.drawable.ic_play_arrow
                )

                // Update song info if it's changed
                val currentSong = service.getCurrentSong()
                if (currentSong != null && currentSong.title != titleView.text.toString()) {
                    titleView.text = currentSong.title
                    artistView.text = currentSong.artist
                    currentSongTitle = currentSong.title
                }

                // Update favorite icon
                favoriteBtn.setImageResource(
                    if (service.isSongFavorite(currentSongTitle ?: ""))
                        R.drawable.ic_favorite
                    else
                        R.drawable.ic_favorite_border
                )

                // Update repeat icon
                repeatBtn.setImageResource(
                    if (service.isRepeatEnabled())
                        R.drawable.ic_repeat_one
                    else
                        R.drawable.ic_repeat
                )

                // Update shuffle icon
                shuffleBtn.setImageResource(
                    if (service.isShuffleEnabled())
                        R.drawable.ic_shuffle_on
                    else
                        R.drawable.ic_shuffle
                )
            }
        }
    }

    private fun safeAction(action: () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            Toast.makeText(context, "Action failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (serviceBound) {
            context?.unbindService(serviceConnection)
            serviceBound = false
        }
    }
}