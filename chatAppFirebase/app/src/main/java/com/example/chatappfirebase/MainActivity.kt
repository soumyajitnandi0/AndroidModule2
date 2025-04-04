package com.example.chatappfirebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), ChatFragment.ChatFragmentListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: ArrayList<UserModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase and user list
        database = FirebaseDatabase.getInstance().reference.child("users")
        userList = ArrayList()

        // Setup RecyclerView
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(userList) { selectedUser -> openChatFragment(selectedUser) }
        binding.rvUsers.adapter = userAdapter

        // Fetch users from database
        fetchUsers()

        // Logout functionality
        binding.fabLogout.setOnClickListener {
            SharedPrefHelper.clearUser(this)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun fetchUsers() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)
                    // Don't show the current logged-in user
                    if (user != null && user.id != SharedPrefHelper.getUser(this@MainActivity)?.id) {
                        userList.add(user)
                    }
                }

                userAdapter.notifyDataSetChanged()

                // Hide RecyclerView if list is empty
                binding.rvUsers.visibility = if (userList.isEmpty()) View.GONE else View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openChatFragment(user: UserModel) {
        val chatFragment = ChatFragment.newInstance(user.id, user.username)

        // Replace frame layout with ChatFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, chatFragment)
            .addToBackStack(null)
            .commit()

        // Hide main UI components
        binding.frameLayout.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.GONE
        binding.fabLogout.visibility = View.GONE
        binding.topBar.visibility = View.GONE
    }

    // Called from ChatFragment when back button is pressed
    override fun onChatClosed() {
        binding.frameLayout.visibility = View.GONE
        binding.rvUsers.visibility = View.VISIBLE
        binding.fabLogout.visibility = View.VISIBLE
        binding.topBar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            onChatClosed() // Restore UI manually
        } else {
            super.onBackPressed()
        }
    }
}
