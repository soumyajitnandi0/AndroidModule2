package com.example.roomdbexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var database: ContactsDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Room.databaseBuilder(applicationContext,ContactsDatabase::class.java,"contactDB").build()

        GlobalScope.launch {
            database.contactDao().insertContact(Contact(0,"yug","8140385672"))
        }
    }

    fun getData(view:View){
        database.contactDao().getContacts().observe(this, Observer{
            Log.d("Roomkey",it.toString())
        })
    }
}