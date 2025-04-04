package com.example.chatappfirebase

import android.content.Context
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
    private var listener: ChatFragmentListener? = null // Interface listener

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

    // Interface for communication with MainActivity
    interface ChatFragmentListener {
        fun onChatClosed()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ChatFragmentListener) {
            listener = context
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

        // Set the chat header
        binding.tvChatHeader.text = "Chat with $receiverUsername"

        // Initialize Firebase reference and chat list
        database = FirebaseDatabase.getInstance().reference.child("Chats")
        chatList = ArrayList()

        // Setup RecyclerView
        binding.rvChats.layoutManager = LinearLayoutManager(requireContext())
        chatAdapter = ChatAdapter(chatList, senderId)
        binding.rvChats.adapter = chatAdapter

        // Load messages
        fetchChats()

        // Send message on click
        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        // Go back to user list
        binding.btnBack.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        listener?.onChatClosed() // Notify MainActivity to show UI again
        requireActivity().supportFragmentManager.popBackStack()
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

            // Save message for sender and receiver
            database.child(senderId).child(receiverId!!).child(messageId).setValue(chatMessage)
            database.child(receiverId!!).child(senderId).child(messageId).setValue(chatMessage)

            binding.etMessage.text.clear()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
