package com.example.myrecyclerdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chat_adapter: Chat_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        val Chat_Items = mutableListOf(
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp),
            Chat_Item("Yug","Arise","12:00",R.drawable.pfp)
        )
        chat_adapter = Chat_Adapter(Chat_Items)
        recyclerView.adapter = chat_adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}