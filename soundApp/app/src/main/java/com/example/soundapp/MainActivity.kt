package com.example.soundapp

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: musicadapter
    private var musicService: musicservice? = null
    private var serviceBound = false
    private lateinit var songs: List<SongData>

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as musicservice.MusicBinder
            musicService = binder.getService()
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            serviceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Request permissions for audio
        requestPermissions()

        recyclerView = findViewById(R.id.music_rv)

        songs = listOf(
            SongData("Bekhayali", "Sandeep Reddy", R.drawable.ic_music_note),
            SongData("Pachtaoge", "B Praak", R.drawable.ic_music_note),
            SongData("Mast Magan", "Arijit Singh", R.drawable.ic_music_note),
            SongData("Agar Tum Sath Ho", "Arijit Singh", R.drawable.ic_music_note),
            SongData("Kinna Sona", "Dhavani BhanuShali", R.drawable.ic_music_note),
            SongData("Lag Ja Gale", "Sachin-Jigar", R.drawable.ic_music_note),
            SongData("Appadi Podu", "Thalapathy Vijay", R.drawable.ic_music_note),
            SongData("Why This Kolaveri Di", "Dhanush", R.drawable.ic_music_note),
            SongData("beedi", "Omkara", R.drawable.ic_music_note),
            SongData("A Thousand Years", "Christina Perri", R.drawable.ic_music_note)
        )

        adapter = musicadapter(songs) { song, position ->
            playSong(position)
            showPlayerFragment(song)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Bind to the music service
        val intent = Intent(this, musicservice::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun requestPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.FOREGROUND_SERVICE)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.FOREGROUND_SERVICE)
        }

        if (!permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            ActivityCompat.requestPermissions(this, permissions, 101)
        }
    }

    private fun playSong(position: Int) {
        val intent = Intent(this, musicservice::class.java).apply {
            action = musicservice.ACTION_PLAY
            putParcelableArrayListExtra(musicservice.EXTRA_SONG_LIST, ArrayList(songs))
            putExtra(musicservice.EXTRA_SONG_INDEX, position)
        }
        startService(intent)
    }

    private fun showPlayerFragment(song: SongData) {
        recyclerView.visibility = View.GONE
        findViewById<View>(R.id.musicPlayer).visibility = View.VISIBLE

        val playerFragment = musicplayerfragment.newInstance(song.title, song.artist)
        supportFragmentManager.beginTransaction()
            .replace(R.id.musicPlayer, playerFragment)
            .addToBackStack(null)
            .commit()

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                recyclerView.visibility = View.VISIBLE
                findViewById<View>(R.id.musicPlayer).visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (serviceBound) {
            unbindService(serviceConnection)
            serviceBound = false
        }
    }
}