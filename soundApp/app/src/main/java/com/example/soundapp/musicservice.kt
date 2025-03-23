package com.example.soundapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class musicservice : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var currentSongIndex = 0
    private var isRepeat = false
    private var isShuffle = false
    private lateinit var songs: List<SongData>
    private lateinit var mediaSession: MediaSessionCompat
    private val binder = MusicBinder()
    private var favoritesList = mutableSetOf<String>()

    private lateinit var audioManager: AudioManager
    private lateinit var audioFocusRequest: AudioFocusRequest

    companion object {
        const val ACTION_PLAY = "com.example.soundapp.PLAY"
        const val ACTION_PAUSE = "com.example.soundapp.PAUSE"
        const val ACTION_PREVIOUS = "com.example.soundapp.PREVIOUS"
        const val ACTION_NEXT = "com.example.soundapp.NEXT"
        const val ACTION_STOP = "com.example.soundapp.STOP"
        const val ACTION_TOGGLE_FAVORITE = "com.example.soundapp.TOGGLE_FAVORITE"
        const val ACTION_TOGGLE_REPEAT = "com.example.soundapp.TOGGLE_REPEAT"
        const val ACTION_TOGGLE_SHUFFLE = "com.example.soundapp.TOGGLE_SHUFFLE"

        const val EXTRA_SONG_INDEX = "SONG_INDEX"
        const val EXTRA_SONG_LIST = "SONG_LIST"

        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "MusicPlayerChannel"
    }

    inner class MusicBinder : Binder() {
        fun getService(): musicservice = this@musicservice
    }

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Initialize MediaSession
        mediaSession = MediaSessionCompat(this, "MusicService")
        mediaSession.setCallback(object : MediaSessionCompat.Callback() {
            override fun onPlay() {
                play()
            }

            override fun onPause() {
                pause()
            }

            override fun onSkipToNext() {
                playNext()
            }

            override fun onSkipToPrevious() {
                playPrevious()
            }

            override fun onStop() {
                stopSelf()
            }
        })

        // Set up AudioFocusRequest for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setOnAudioFocusChangeListener { focusChange ->
                    when (focusChange) {
                        AudioManager.AUDIOFOCUS_LOSS -> {
                            pause()
                        }
                        AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                            pause()
                        }
                        AudioManager.AUDIOFOCUS_GAIN -> {
                            play()
                        }
                    }
                }
                .build()
        }

        // Create notification channel
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                intent.getParcelableArrayListExtra<SongData>(EXTRA_SONG_LIST)?.let {
                    songs = it
                    currentSongIndex = intent.getIntExtra(EXTRA_SONG_INDEX, 0)
                    playSong(currentSongIndex)
                } ?: run {
                    play()
                }
            }
            ACTION_PAUSE -> pause()
            ACTION_NEXT -> playNext()
            ACTION_PREVIOUS -> playPrevious()
            ACTION_STOP -> {
                stopForeground(true)
                stopSelf()
            }
            ACTION_TOGGLE_FAVORITE -> toggleFavorite()
            ACTION_TOGGLE_REPEAT -> toggleRepeat()
            ACTION_TOGGLE_SHUFFLE -> toggleShuffle()
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Player",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Music player controls"
                setShowBadge(false)
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun requestAudioFocus(): Boolean {
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.requestAudioFocus(audioFocusRequest)
        } else {
            @Suppress("DEPRECATION")
            audioManager.requestAudioFocus(
                { focusChange ->
                    when (focusChange) {
                        AudioManager.AUDIOFOCUS_LOSS -> pause()
                        AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> pause()
                        AudioManager.AUDIOFOCUS_GAIN -> play()
                    }
                },
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }

        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }

    private fun playSong(songIndex: Int) {
        if (!::songs.isInitialized || songs.isEmpty() || songIndex < 0 || songIndex >= songs.size) {
            return
        }

        // Release current MediaPlayer if exists
        mediaPlayer?.release()

        // Request audio focus
        if (!requestAudioFocus()) {
            return
        }

        // Initialize MediaPlayer
        val song = songs[songIndex]
        val songResId = resources.getIdentifier(
            song.title.lowercase().replace(" ", "_"),
            "raw",
            packageName
        )

        if (songResId <= 0) {
            return
        }

        try {
            mediaPlayer = MediaPlayer.create(this, songResId).apply {
                setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                setOnCompletionListener {
                    if (isRepeat) {
                        playSong(currentSongIndex)
                    } else {
                        playNext()
                    }
                }
                start()
            }

            // Update current song index
            currentSongIndex = songIndex

            // Update MediaSession state
            updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)

            // Show notification
            showNotification()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun play() {
        if (mediaPlayer == null) {
            if (::songs.isInitialized && songs.isNotEmpty()) {
                playSong(currentSongIndex)
            }
        } else if (!mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
            updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
            showNotification()
        }
    }

    private fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                updatePlaybackState(PlaybackStateCompat.STATE_PAUSED)
                showNotification()
            }
        }
    }

    private fun playNext() {
        if (!::songs.isInitialized || songs.isEmpty()) {
            return
        }

        val nextIndex = if (isShuffle) {
            (0 until songs.size).random()
        } else {
            (currentSongIndex + 1) % songs.size
        }

        playSong(nextIndex)
    }

    private fun playPrevious() {
        if (!::songs.isInitialized || songs.isEmpty()) {
            return
        }

        val prevIndex = if (isShuffle) {
            (0 until songs.size).random()
        } else {
            if (currentSongIndex > 0) currentSongIndex - 1 else songs.size - 1
        }

        playSong(prevIndex)
    }

    private fun toggleFavorite() {
        if (!::songs.isInitialized || songs.isEmpty()) {
            return
        }

        val currentSong = songs[currentSongIndex].title
        if (favoritesList.contains(currentSong)) {
            favoritesList.remove(currentSong)
        } else {
            favoritesList.add(currentSong)
        }

        // Update notification to reflect changes
        showNotification()
    }

    private fun toggleRepeat() {
        isRepeat = !isRepeat
        showNotification()
    }

    private fun toggleShuffle() {
        isShuffle = !isShuffle
        showNotification()
    }

    private fun updatePlaybackState(state: Int) {
        val stateBuilder = PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                        PlaybackStateCompat.ACTION_STOP
            )
            .setState(state, 0, 1f)

        mediaSession.setPlaybackState(stateBuilder.build())
    }

    private fun showNotification() {
        if (!::songs.isInitialized || songs.isEmpty()) {
            return
        }

        val currentSong = songs[currentSongIndex]
        val isPlaying = mediaPlayer?.isPlaying ?: false
        val isFavorite = favoritesList.contains(currentSong.title)

        // Create intents for notification actions
        val playPauseIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, musicservice::class.java).setAction(if (isPlaying) ACTION_PAUSE else ACTION_PLAY),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val nextIntent = PendingIntent.getService(
            this,
            1,
            Intent(this, musicservice::class.java).setAction(ACTION_NEXT),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val prevIntent = PendingIntent.getService(
            this,
            2,
            Intent(this, musicservice::class.java).setAction(ACTION_PREVIOUS),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val favoriteIntent = PendingIntent.getService(
            this,
            3,
            Intent(this, musicservice::class.java).setAction(ACTION_TOGGLE_FAVORITE),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val repeatIntent = PendingIntent.getService(
            this,
            4,
            Intent(this, musicservice::class.java).setAction(ACTION_TOGGLE_REPEAT),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val shuffleIntent = PendingIntent.getService(
            this,
            5,
            Intent(this, musicservice::class.java).setAction(ACTION_TOGGLE_SHUFFLE),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent for opening MainActivity when notification is clicked
        val contentIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Get album art
        val albumArt = BitmapFactory.decodeResource(resources, currentSong.image)

        // Create notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music_note)
            .setLargeIcon(albumArt)
            .setContentTitle(currentSong.title)
            .setContentText(currentSong.artist)
            .setContentIntent(contentIntent)
            .setOngoing(isPlaying)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                R.drawable.ic_skip_previous,
                "Previous",
                prevIntent
            )
            .addAction(
                if (isPlaying) R.drawable.pauseicon else R.drawable.ic_play_arrow,
                if (isPlaying) "Pause" else "Play",
                playPauseIntent
            )
            .addAction(
                R.drawable.ic_skip_next,
                "Next",
                nextIntent
            )
            .addAction(
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border,
                "Favorite",
                favoriteIntent
            )
            .addAction(
                if (isRepeat) R.drawable.ic_repeat_one else R.drawable.ic_repeat,
                "Repeat",
                repeatIntent
            )
            .addAction(
                if (isShuffle) R.drawable.ic_shuffle_on else R.drawable.ic_shuffle,
                "Shuffle",
                shuffleIntent
            )
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.abandonAudioFocusRequest(audioFocusRequest)
        } else {
            @Suppress("DEPRECATION")
            audioManager.abandonAudioFocus(null)
        }

        mediaSession.release()
        super.onDestroy()
    }

    // Public methods for binding
    fun getCurrentSong(): SongData? {
        return if (::songs.isInitialized && currentSongIndex >= 0 && currentSongIndex < songs.size) {
            songs[currentSongIndex]
        } else {
            null
        }
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    fun isSongFavorite(title: String): Boolean = favoritesList.contains(title)

    fun isRepeatEnabled(): Boolean = isRepeat

    fun isShuffleEnabled(): Boolean = isShuffle
}