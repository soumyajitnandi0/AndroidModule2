package com.example.chatappfirebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: ArrayList<UserModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference.child("users")
        userList = ArrayList()

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(userList) { selectedUser -> openChatFragment(selectedUser) }
        binding.rvUsers.adapter = userAdapter

        fetchUsers() // ✅ Ensure this is called

        binding.fabLogout.setOnClickListener {
            SharedPrefHelper.clearUser(this)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun fetchUsers() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear() // Clear previous data

                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)

                    if (user != null && user.id != SharedPrefHelper.getUser(this@MainActivity)?.id) {
                        userList.add(user)
                    }
                }

                userAdapter.notifyDataSetChanged()

                if (userList.isEmpty()) {
                    binding.rvUsers.visibility = View.GONE
                } else {
                    binding.rvUsers.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun openChatFragment(user: UserModel) {
        val chatFragment = ChatFragment.newInstance(user.id, user.username)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, chatFragment)
            .addToBackStack(null) // Ensures back button works properly
            .commit()

        binding.frameLayout.visibility = android.view.View.VISIBLE
        binding.rvUsers.visibility = android.view.View.GONE
        binding.fabLogout.visibility = android.view.View.GONE
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            binding.frameLayout.visibility = android.view.View.GONE
            binding.rvUsers.visibility = android.view.View.VISIBLE
            binding.fabLogout.visibility = android.view.View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }
}
