package com.example.chatappfirebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappfirebase.databinding.FragmentChatBinding
import com.google.firebase.database.*

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var database: DatabaseReference
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatList: ArrayList<ChatModel>
    private var receiverId: String? = null
    private var receiverUsername: String? = null
    private lateinit var senderId: String

    companion object {
        private const val ARG_RECEIVER_ID = "receiverId"
        private const val ARG_RECEIVER_USERNAME = "receiverUsername"

        fun newInstance(receiverId: String, receiverUsername: String): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putString(ARG_RECEIVER_ID, receiverId)
            args.putString(ARG_RECEIVER_USERNAME, receiverUsername)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receiverId = it.getString(ARG_RECEIVER_ID)
            receiverUsername = it.getString(ARG_RECEIVER_USERNAME)
        }
        senderId = SharedPrefHelper.getUser(requireContext())?.id ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvChatHeader.text = "Chat with $receiverUsername"

        database = FirebaseDatabase.getInstance().reference.child("Chats")
        chatList = ArrayList()

        binding.rvChats.layoutManager = LinearLayoutManager(requireContext())
        chatAdapter = ChatAdapter(chatList, senderId)
        binding.rvChats.adapter = chatAdapter

        fetchChats()

        binding.btnSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun fetchChats() {
        val chatReference = database.child(senderId).child(receiverId!!)
        chatReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (chatSnapshot in snapshot.children) {
                    val chat = chatSnapshot.getValue(ChatModel::class.java)
                    if (chat != null) {
                        chatList.add(chat)
                    }
                }
                chatAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendMessage() {
        val messageText = binding.etMessage.text.toString().trim()
        if (messageText.isNotEmpty() && receiverId != null) {
            val messageId = database.push().key ?: return
            val chatMessage = ChatModel(messageId, senderId, receiverId!!, messageText)

            database.child(senderId).child(receiverId!!).child(messageId).setValue(chatMessage)
            database.child(receiverId!!).child(senderId).child(messageId).setValue(chatMessage)

            binding.etMessage.text.clear()
        }
    }
}
