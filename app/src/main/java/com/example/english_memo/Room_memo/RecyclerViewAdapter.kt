package com.example.english_memo.Room_memo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.english_memo.R
import kotlinx.android.synthetic.main.activity_translage_memo.*
import kotlinx.android.synthetic.main.note_item.view.*

class RecyclerViewAdapter(val listener: RowClickListener, private var items : ArrayList<UserEntity>): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var context1: Context? = null
    lateinit var trans : Translage_memo

    fun setListData(data: ArrayList<UserEntity>) {
        this.items = data
        this.context1 = context1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return MyViewHolder(inflater, listener)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClickupdateListener(items[position])
        }
        holder.bind(items[position])

        if (this::trans.isInitialized) {
            trans.contents_visible.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked) {
                    holder.itemView.item_title.visibility = View.INVISIBLE
                } else {
                    holder.itemView.item_title.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(view: View, val listener: RowClickListener) : RecyclerView.ViewHolder(view) {

        val tv_title = view.item_title
        val tv_date = view.item_date
        val delete = view.delete

        fun bind(data: UserEntity) {
            tv_title.text = data.title

            tv_date.text = data.date

            delete.setOnClickListener {
                listener.onDeleteUserClickListener(data)
            }

        }

    }

    interface RowClickListener {


        fun onDeleteUserClickListener(user: UserEntity)
        fun onItemClickListener(user: UserEntity)
        fun onItemClickupdateListener(user: UserEntity)
        fun onItemvisible(user: UserEntity)
    }

}