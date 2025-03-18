package com.example.authusingsqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.authusingsqlite.databinding.ActivityLoginBinding

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databasehelper: databasehelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databasehelper = databasehelper(this)
        binding.myLoginButton.setOnClickListener{
            val username = binding.myloginUserName.text.toString()
            val password = binding.myLoginPassword.text.toString()
            loginDatabase(username,password)
        }
        binding.myLoginTextView.setOnClickListener{
            startActivity(Intent(this,signin::class.java))
            finish()
        }
    }

    private fun loginDatabase(username:String,password:String){
        val userExists = databasehelper.readUser(username,password)
        if(userExists) {
            Toast.makeText(applicationContext, "login successfull", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            Toast.makeText(applicationContext, "login failed", Toast.LENGTH_LONG).show()
        }
    }
}