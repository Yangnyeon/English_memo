package com.example.english_memo.Game

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.english_memo.R
import java.lang.Class as DF

class Game_Adapter(private val context : Context) : RecyclerView.Adapter<Game_Adapter.ViewHolder>() {


    lateinit var profileList: ArrayList<Profiles>




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.game_item, parent, false)


        return ViewHolder(view).apply {



            itemView.setOnClickListener {
                val curPos : Int = adapterPosition
                val profile : Profiles = profileList.get(curPos)


                //startActivity(item[profile])

                //Toast.makeText(context, "장소 : ${profile.Game_imagetext}", Toast.LENGTH_SHORT).show()


            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.game_iamge.setImageResource(profileList.get(position).Game_iamge)
        holder.game_imagetext.text = profileList.get(position).Game_imagetext

        holder.itemView.setOnClickListener {
           // var Dictation = Intent(holder.itemView.context, Real_Community2::class.java)
           // var word = Intent(holder.itemView.context, Game_word::class.java)
          //  var Dictation = Real_Community2::class.java
           // var word = Game_word::class.java
          //  var textlist = arrayListOf<String>()
         //   textlist.add(Dictation)
        //    textlist.add(word.toString())

        }
    }

    override fun getItemCount(): Int {
        return profileList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val game_iamge = itemView.findViewById<ImageView>(R.id.game_image)
        val game_imagetext = itemView.findViewById<TextView>(R.id.game_imagetext)
    }

}