package com.example.english_memo.Real_Community

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util.getSnapshot
import com.example.english_memo.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.model.DocumentKey

class ListAdapter(val itemList: ArrayList<ListLayout>,val context: Context): RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.number.text = itemList[position].number
        holder.community_date.text = itemList[position].com_date
        holder.password.text = itemList[position].password



        holder.itemView.setOnClickListener {

            var title = itemList[position].name
            var date = itemList[position].number
            var content = itemList[position].com_date
            var doc = itemList[position].doc

            val go_board = Intent(context, Community_holdedr::class.java)
            go_board.putExtra("board_title", title)
            go_board.putExtra("board_date", date)
            go_board.putExtra("board_content", content)
            go_board.putExtra("board_doc", doc)

            context.startActivity(go_board)
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.list_tv_name)
        val number: TextView = itemView.findViewById(R.id.list_tv_number)
        val community_date : TextView = itemView.findViewById(R.id.community_date)
        val doc : TextView = itemView.findViewById(R.id.list_doc);
        val password : TextView = itemView.findViewById(R.id.list_password)
    }
}