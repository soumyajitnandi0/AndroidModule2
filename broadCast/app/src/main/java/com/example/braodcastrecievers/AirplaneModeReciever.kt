package com.example.braodcastrecievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeReciever: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isOn = intent?.getBooleanExtra("state", false)
        if(isOn == true) {
            Toast.makeText(context, "Airplane mode on", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Airplane mode off", Toast.LENGTH_SHORT).show()
        }
    }
}