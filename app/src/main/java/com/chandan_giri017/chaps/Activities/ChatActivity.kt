package com.chandan_giri017.chaps.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chandan_giri017.chaps.Adapters.MessagesAdapter
import com.chandan_giri017.chaps.Adapters.UsersAdapter
import com.chandan_giri017.chaps.Classes.Message
import com.chandan_giri017.chaps.Classes.User
import com.chandan_giri017.chaps.R
import com.chandan_giri017.chaps.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var messages: ArrayList<Message>
    lateinit var chatsRecycler: RecyclerView
    lateinit var database: FirebaseDatabase
    lateinit var senderRoom: String
    lateinit var receiverRoom: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        database = FirebaseDatabase.getInstance()
        ///////////hide title in default action bar /////////////////////
        supportActionBar!!.setDisplayShowTitleEnabled(false)

///////////get  and set name and profile image fron previous activity intent/////////////
        val name = intent.getStringExtra("name")
        val profileImage = intent.getStringExtra("profileImage")

        binding.name.setText(name)
        Glide.with(applicationContext).load(profileImage).placeholder(R.drawable.avatar)
            .into(binding.profileImage)
        //////////////////////////////////////////////////////////////////

        //////////ob Back button pressed///////////
        binding.backButton.setOnClickListener {
            overridePendingTransition(0, 0)
            finish()
        }
//////////////////////////////////////

        val receiverUid = intent.getStringExtra("receiverUid").toString()
        val senderUid = FirebaseAuth.getInstance().uid.toString()

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid

///////////// Online status of user ///////////////////////////
        database.reference.child("Presence").child(receiverUid)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val status = snapshot.getValue(String::class.java).toString()
                        if (status == "Online") {
                            database.reference.child("Presence").child(receiverRoom)
                                .addValueEventListener(object : ValueEventListener {

                                    override fun onDataChange(snapshot: DataSnapshot) {

                                        if (snapshot.exists()) {
                                            val typingStatus =
                                                snapshot.getValue(String::class.java).toString()
                                            binding.status.setText(typingStatus)
                                        } else {
                                            binding.status.setText(status)
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {

                                    }

                                })
                        } else {
                            binding.status.setText(status)
                            binding.status.visibility = View.VISIBLE
                        }


                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        //////////// finished online status//////////////////

        //////////chats loading /////////////////////



        chatsRecycler = binding.chatsRecycler

        chatsRecycler.setHasFixedSize(true)
        messages = arrayListOf<Message>()




        database.reference.child("Chats").child(senderRoom).child("Messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        messages.clear()
                        for (messagesSnapshot in snapshot.children) {
                            val message = messagesSnapshot.getValue(Message::class.java)
                            messages.add(message!!)
                        }
                        chatsRecycler.adapter = MessagesAdapter(applicationContext, messages)
                        chatsRecycler.smoothScrollToPosition((chatsRecycler.adapter as MessagesAdapter).itemCount);
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
//////////messages stored in arraylist////////////



        chatsRecycler.adapter = MessagesAdapter(applicationContext, messages)
///////////messages sent to adapter in recycler view /////////////////


//////////send new message ////////////////////////////////////////////
        binding.sendButton.setOnClickListener {


            val messageText = binding.messageBox.text.toString()
            if (messageText.trim().isNotEmpty()) {
                val date = Date()
                val message = Message(messageText, senderUid, date.time)
                binding.messageBox.setText("")

                var randomKey = database.reference.push().key
                val lastMsgObj: HashMap<String, Any> = HashMap()
                lastMsgObj["lastMsg"] = message.message
                lastMsgObj["lastMsgTime"] = date.time

                database.reference.child("Chats").child(senderRoom).updateChildren(lastMsgObj)
                database.reference.child("Chats").child(receiverRoom).updateChildren(lastMsgObj)


                ////////////////////
                database.reference.child("Chats").child(senderRoom).child("Messages")
                    .child(randomKey.toString())
                    .setValue(message).addOnSuccessListener {

                        database.reference.child("Chats").child(receiverRoom).child("Messages")
                            .child(randomKey.toString())
                            .setValue(message).addOnSuccessListener {

                            }


                    }
            }


        }

        ////////new message sent to the server ///////////////////

        //////////for typing status ////////////////////////
        val handler = Handler()

        binding.messageBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                database.reference.child("Presence").child(senderRoom).setValue("typing..")
                handler.removeCallbacks(userStoppedTyping)
                handler.postDelayed(userStoppedTyping, 1000)
            }

            var userStoppedTyping =
                Runnable {
                    database.reference.child("Presence").child(senderRoom).setValue("Online")
                }

        })
////////////////////finished typing status

    }

    /////////on resume method for making default status online while chat is open ///////////////
    override fun onResume() {
        super.onResume()
        database.reference.child("Presence").child(FirebaseAuth.getInstance().uid.toString())
            .setValue("Online")
    }

    ///////////// finished /////////////////////


}