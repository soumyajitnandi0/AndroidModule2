package com.example.soundapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class musicservice : Service() {
    private var mediaPlayer: MediaPlayer? = null

    companion object {
        const val ACTION_PLAY = "PLAY"
        const val ACTION_PAUSE = "PAUSE"
        const val ACTION_STOP = "STOP"
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songResId = intent?.getIntExtra("SONG_RES_ID", -1) ?: -1
        val action = intent?.action

        if (songResId == -1) return START_NOT_STICKY

        when (action) {
            ACTION_PLAY -> {
                if (mediaPlayer != null) {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                }

                mediaPlayer = MediaPlayer.create(this, songResId)
                mediaPlayer?.start()
            }

            ACTION_PAUSE -> {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                }
            }

            ACTION_STOP -> {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}
