package com.example.videocallapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class Login : AppCompatActivity() {
    private lateinit var usenameinput : EditText
    private lateinit var loginbtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        usenameinput = findViewById(R.id.username_input)
        loginbtn = findViewById(R.id.login_btn)

        loginbtn.setOnClickListener {
            val username = usenameinput.text.toString()
            val config = ZegoUIKitPrebuiltCallInvitationConfig()
            ZegoUIKitPrebuiltCallInvitationService.init(application,Constants.appId,Constants.appSign,username,username,config)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
            finish()
        }
    }
}