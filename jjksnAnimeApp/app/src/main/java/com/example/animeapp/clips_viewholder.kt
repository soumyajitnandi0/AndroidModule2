package com.example.animeapp

import android.net.Uri
import android.view.View
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView

class clips_viewholder(viewholder: View): RecyclerView.ViewHolder(viewholder){
    private val videoHolder: PlayerView = viewholder.findViewById(R.id.videoClips)
    private var player : ExoPlayer? = null
    fun bind(reel:clips_data){
        player = ExoPlayer.Builder(videoHolder.context).build()
        videoHolder.player = player
        val uri = Uri.parse("android.resource://" + videoHolder.context.packageName + "/" + reel.video)
        val mediaItem = MediaItem.fromUri(uri)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
        player?.repeatMode = ExoPlayer.REPEAT_MODE_ONE
    }
    fun releasePlayer() {
        player?.release()
        player = null
    }
}