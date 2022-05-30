package com.chandan_giri017.chaps.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chandan_giri017.chaps.Activities.ChatActivity
import com.chandan_giri017.chaps.R
import com.chandan_giri017.chaps.Classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class UsersAdapter(val context: Context, private val usersList: ArrayList<User>) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_conversation, parent, false)
        return UsersViewHolder(view)

    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        val curr = usersList[position]
        val senderId = FirebaseAuth.getInstance().uid

        val senderRoom = senderId + curr.uid

        FirebaseDatabase.getInstance().reference
            .child("Chats")
            .child(senderRoom)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val lastMsg = snapshot.child("lastMsg").getValue(String::class.java)
                        val time = snapshot.child("lastMsgTime").getValue(Long::class.java)!!!!
                        val dateFormat = SimpleDateFormat("hh:mm a")
                        holder.msgTime.text = dateFormat.format(Date(time))
                        holder.lastMsg.text = lastMsg
                    } else {
                        holder.lastMsg.text = "Tap to chat"
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })


        holder.userName.text = curr.name
        Glide.with(context).load(curr.profileImage)
            .placeholder(R.drawable.avatar)
            .into(holder.profileImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("name", curr.name)
            intent.putExtra("profileImage", curr.profileImage)
            intent.putExtra("receiverUid", curr.uid.toString())
            context.startActivity(intent)


        }


    }

    override fun getItemCount(): Int {

        return usersList.size

    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userName)
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        val lastMsg: TextView = itemView.findViewById(R.id.lastMessage)
        val msgTime: TextView = itemView.findViewById(R.id.messageTime)


    }


}

