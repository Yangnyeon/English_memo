package com.example.english_memo.ai_Project

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.english_memo.databinding.ChatMessageBinding

class koGpt_AdaPter (var comments : ArrayList<Generation>, val context: Context) : RecyclerView.Adapter<koGpt_AdaPter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): koGpt_AdaPter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ChatMessageBinding.inflate(layoutInflater)
        return ViewHolder(binding)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(comments[position])


        if(comments[position].tokens == 999) {
            holder.messageItemLinearlayoutMain.gravity = Gravity.RIGHT;
            holder.chatPerson.gravity = Gravity.RIGHT
            holder.chatPerson.text = "나"
            holder.chatMessage.gravity = Gravity.RIGHT

            holder.chatMessage.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            holder.messageItemLinearlayoutMain.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )


        } else {


            holder.messageItemLinearlayoutMain.gravity = Gravity.LEFT;
            holder.chatPerson.gravity = Gravity.LEFT
            holder.chatPerson.text = "상대"
            holder.chatMessage.gravity = Gravity.LEFT

            holder.chatMessage.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            holder.messageItemLinearlayoutMain.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

    }


    override fun getItemCount(): Int {
        return comments.size
    }

    inner class ViewHolder(private val binding: ChatMessageBinding): RecyclerView.ViewHolder(binding.root){

        var messageItemLinearlayoutMain : LinearLayout = binding.messageItemLinearlayoutMain


        var chatPerson : TextView = binding.chatPerson

        var chatMessage : TextView = binding.messageItemTextViewMessage

        fun bind(item : Generation) {

            binding.koGpt = item

        }
    }
}

