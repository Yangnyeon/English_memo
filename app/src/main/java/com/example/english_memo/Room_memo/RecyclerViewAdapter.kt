package com.example.english_memo.Room_memo

import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.english_memo.R
import kotlinx.android.synthetic.main.activity_translage_memo.*
import kotlinx.android.synthetic.main.note_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapter(val listener: RowClickListener, private var itemList: ArrayList<UserEntity>, var context: Context): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() ,TextToSpeech.OnInitListener{

    lateinit var trans : Translage_memo
//
    private var userList = ArrayList<UserEntity>()

    private lateinit var textToSpeech: TextToSpeech

    lateinit var newHolder : MyViewHolder

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
            listener.onItemClickupdateListener(userList[position])
        }
        holder.bind(userList[position])

        initializeTextToSpeech()
        releaseTextToSpeech()

        holder.word_Speaker.setOnClickListener {
            textToSpeech.speak(userList[position].title, TextToSpeech.QUEUE_FLUSH, null, null)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(view: View, val listener: RowClickListener) : RecyclerView.ViewHolder(view) {

        val tv_title = view.item_title
        val tv_date = view.item_date
        val delete = view.delete

        var word_Speaker : ImageView = view.findViewById(R.id.word_Speaker)

        var item_title : TextView = view.findViewById(R.id.item_title)
        var item_word : TextView = view.findViewById(R.id.item_date)

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


    fun setData(user : List<UserEntity>){
        userList = user as ArrayList<UserEntity>
        notifyDataSetChanged()
    }


    fun setList(englishes: List<UserEntity>) {
        userList.clear()
        userList.addAll(englishes as ArrayList<UserEntity>)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // TextToSpeech 초기화 성공 시 언어 설정
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // 언어 데이터가 누락되었거나 지원되지 않을 때의 처리
                // 원하는 대안을 선택하여 처리
            }
        } else {
            // TextToSpeech 초기화 실패 시의 처리
            // 원하는 대안을 선택하여 처리
        }
    }

    fun initializeTextToSpeech() {
        // TextToSpeech 객체 초기화
        textToSpeech = TextToSpeech(context, this)
    }

    fun releaseTextToSpeech() {
        // TextToSpeech 객체 해제
        textToSpeech.stop()
        textToSpeech.shutdown()
    }



}