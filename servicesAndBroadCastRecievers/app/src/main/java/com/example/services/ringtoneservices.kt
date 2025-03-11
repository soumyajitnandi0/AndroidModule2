package com.example.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.provider.Settings

class ringtoneservices:Service() {
    private lateinit var mPlayer:MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mPlayer=MediaPlayer.create(this, R.raw.anime)
        mPlayer.isLooping=true
        mPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer.stop()
    }
}