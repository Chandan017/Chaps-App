package com.chandan_giri017.chaps.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chandan_giri017.chaps.Classes.Message
import com.chandan_giri017.chaps.R
import com.google.firebase.auth.FirebaseAuth


class MessagesAdapter(val context: Context, private val messages: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    private val ITEM_SENT=0
    private val ITEM_RECEIVE=1


    override fun getItemViewType(position: Int): Int {
        val curr = messages[position]
        if(FirebaseAuth.getInstance().uid.equals(curr.senderId))
        {
            return ITEM_SENT
        }
        return ITEM_RECEIVE

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==ITEM_SENT)
        {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_send, parent, false)
            return SentViewHolder(view)
        }
        else
        {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_receive, parent, false)
            return ReceiveViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currMessage= messages[position]



        if(holder::class == SentViewHolder::class)
        {
            val viewHolder :SentViewHolder = holder as SentViewHolder
            viewHolder.message.text = currMessage.message


        }
        else
        {
            val viewHolder :ReceiveViewHolder = holder as ReceiveViewHolder
            viewHolder.message.text = currMessage.message

        }

    }

    public override fun getItemCount(): Int {
      return messages.size
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val message:TextView = itemView.findViewById(R.id.sentMessage)
        val feeling: ImageView = itemView.findViewById(R.id.reaction)

    }
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val message :TextView= itemView.findViewById(R.id.receivedMessage)
        val feeling:ImageView = itemView.findViewById(R.id.reaction)

    }

}