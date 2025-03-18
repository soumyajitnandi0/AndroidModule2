package com.example.authusingsqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.authusingsqlite.databinding.ActivitySigninBinding

class signin : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var databasehelper: databasehelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databasehelper = databasehelper(this)
        binding.mySignupButton.setOnClickListener{
            val username = binding.mySignupUsername.text.toString()
            val password = binding.mySignupPassword.text.toString()
            signupDatabase(username,password)
        }
        binding.mySignupTextView.setOnClickListener{
            startActivity(Intent(this,login::class.java))
            finish()
        }
    }

    private fun signupDatabase(username:String,password:String){
        val insertedRowid = databasehelper.insertUser(username,password)
        if(insertedRowid != -1L){
            Toast.makeText(applicationContext,"sign up successfull", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,login::class.java))
            finish()
        }else{
            Toast.makeText(applicationContext,"sign up failed", Toast.LENGTH_LONG).show()
        }
    }
}