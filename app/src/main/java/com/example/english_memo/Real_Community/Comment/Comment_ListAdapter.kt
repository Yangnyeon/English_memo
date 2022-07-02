package com.example.english_memo.Real_Community.Comment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.english_memo.R
import com.example.english_memo.Real_Community.ListAdapter
import com.example.english_memo.Real_Community.ListLayout

class Comment_ListAdapter(val itemList: ArrayList<Comment_ListLayout>, val context: Context): RecyclerView.Adapter<Comment_ListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Comment_ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_comment_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Comment.text = itemList[position].Comment
        holder.date.text = itemList[position].Date
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val Comment: TextView = itemView.findViewById(R.id.comment123)
        val date: TextView = itemView.findViewById(R.id.comment_date123)


    }

}