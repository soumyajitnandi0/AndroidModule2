package com.example.contentrecievers

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        viewContact()
    }

    fun viewContact(){
        val contact: Cursor? = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)
        if (contact != null && contact.moveToFirst()){
            do{
                val name: String = contact.getString(contact.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                Log.d("getvalue",name)
            }while (contact.moveToNext())
            contact.close()
        }
    }
}