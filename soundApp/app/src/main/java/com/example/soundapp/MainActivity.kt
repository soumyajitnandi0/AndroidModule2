package com.example.soundapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: musicadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.music_rv)

        val songs = listOf(
            songdata("Bekhayali", "Sandeep Reddy", R.drawable.ic_music_note),
            songdata("Pachtaoge", "B Praak", R.drawable.ic_music_note),
            songdata("Mast Magan", "Arijit Singh", R.drawable.ic_music_note),
            songdata("Agar Tum Sath Ho", "Arijit Singh", R.drawable.ic_music_note),
            songdata("Kinna Sona","Dhavani BhanuShali",R.drawable.ic_music_note),
            songdata("Lag Ja Gale","Sachin-Jigar",R.drawable.ic_music_note),
            songdata("Appadi Podu","Thalapathy Vijay",R.drawable.ic_music_note),
            songdata("Why This Kolaveri Di","Dhanush",R.drawable.ic_music_note),
            songdata("beedi","Omkara",R.drawable.ic_music_note),
            songdata("A Thousand Years","Christina Perri",R.drawable.ic_music_note)
        )

        adapter = musicadapter(songs) { song ->
            showPlayerFragment(song)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showPlayerFragment(song: songdata) {
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
}
