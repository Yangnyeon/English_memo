package com.example.english_memo.rxjavaTranning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.english_memo.databinding.NoteItemBinding
import java.util.*

class English_Adapter(listener: OnItemClick) : RecyclerView.Adapter<English_Adapter.TodoViewHolder>() {


    private val mCallback = listener

    private val items = ArrayList<English>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(items[position])
        // holder.bind123(itemList[position])


        holder.itemView.setOnClickListener {

        }
    }


    inner class TodoViewHolder(private val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(english : English) {

            binding.english = english

        }
    }

    /*
    fun setList(English: List<English>) {
        items.clear()
        items.addAll(English)
    }

     */
    fun setList(english: List<English>) {
        items.clear()
        items.addAll(english)
    }



}