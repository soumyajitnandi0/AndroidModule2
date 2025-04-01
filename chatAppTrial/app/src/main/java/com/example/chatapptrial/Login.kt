package com.example.chatapptrial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatapptrial.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val savedUserId = sharedPreferences.getString("userName", null)
        if (savedUserId != null) {
            startActivity(Intent(this@Login, MainActivity::class.java))
            finish()
            return
        }

        firebaseDatabase = FirebaseDatabase.getInstance()
        databseReference = firebaseDatabase.reference.child("users")

        binding.signupTextview.setOnClickListener{
            startActivity(Intent(this@Login, Signup::class.java))
            finish()
        }

        binding.signupBtn.setOnClickListener{
            val usename = binding.signupUsername.text.toString()
            val password = binding.signupPassword.text.toString()
            if(usename.isNotEmpty() && password.isNotEmpty()){
                loginUser(usename,password)
            }else{
                Toast.makeText(this@Login,"All fields are mandatory",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun loginUser(usename:String,password:String){
        databseReference.orderByChild("username").equalTo(usename)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val userData = userSnapshot.getValue(UserModel::class.java)

                            if (userData?.password == password) {
                                Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()

                                val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("userName", userData.username)
                                editor.apply()

                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                return
                            } else {
                                Toast.makeText(this@Login, "Wrong Password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@Login, "User Doesn't Exist", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Login, "Database Error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}