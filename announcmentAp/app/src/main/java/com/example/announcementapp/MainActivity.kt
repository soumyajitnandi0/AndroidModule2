package com.example.announcementapp

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.announcementapp.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var adapter: announcementadapter
    private lateinit var announcementList: MutableList<announcementdata>
    private var lastNotifiedTitle: String? = null // Tracks the last notified announcement title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request notification permission (for Android 13+)
        requestNotificationPermission()

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Announcement")

        // Setup RecyclerView and Adapter
        announcementList = mutableListOf()
        adapter = announcementadapter(announcementList)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        // Fetch data from Firebase and trigger notifications
        fetchData()
    }

    private fun fetchData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                announcementList.clear()
                var lastAnnouncement: announcementdata? = null

                for (data in snapshot.children) {
                    val title = data.key ?: "New Announcement"
                    val content = data.value?.toString() ?: "Check the app for details"

                    Log.d("FirebaseData", "Title: $title, Content: $content")
                    val announcement = announcementdata(title, content)
                    announcementList.add(announcement)
                    lastAnnouncement = announcement
                }

                adapter.notifyDataSetChanged()

                // Show notification only if there is a new announcement
                lastAnnouncement?.let {
                    if (lastNotifiedTitle != it.title) {
                        sendNotification(it.title!!, it.announcement!!)
                        lastNotifiedTitle = it.title
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error loading announcements!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to send a notification
    private fun sendNotification(title: String, message: String) {
        // Check permission before sending notification (for Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Log.d("Notification", "Permission not granted. Notification not sent.")
                return
            }
        }

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, (application as App).channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    // Request notification permission for Android 13+
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Handle permission request result
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("Permission", "Notification permission granted.")
        } else {
            Toast.makeText(this, "Notification permission is required to receive alerts.", Toast.LENGTH_LONG).show()
        }
    }
}
