package com.example.chatapptrial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatapptrial.databinding.ActivitySignupBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

         binding.loginTextview.setOnClickListener {
             startActivity(Intent(this@Signup, Login::class.java))
             finish()
         }

        binding.signupBtn.setOnClickListener {
            val username = binding.signupUsername.text.toString()
            val password = binding.signupPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                signupUser(username, password)
            }else{
                Toast.makeText(this@Signup,"All fields are mandatory",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signupUser(username:String,password:String){
        databaseReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.exists()){
                        val id = databaseReference.push().key
                        val userData = UserModel(id,username,password)
                        databaseReference.child(id!!).setValue(userData)
                        Toast.makeText(this@Signup,"Signup Successfully",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@Signup,Login::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@Signup,"User Already Exist",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Signup,"Database Error",Toast.LENGTH_SHORT).show()
                }
            })
    }
}