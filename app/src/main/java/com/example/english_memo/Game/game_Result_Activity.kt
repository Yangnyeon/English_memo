package com.example.english_memo.Game

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.english_memo.R
import com.example.english_memo.rxjavaTranning.OnItemClick
import kotlinx.android.synthetic.main.activity_game_result.*

class game_Result_Activity(context : Context,var incorrect_Count : Int, OnItemClick : OnItemClick) : Dialog(context), View.OnClickListener {

    var ItemClick : OnItemClick ?= null

    init {
        this.ItemClick = OnItemClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_result)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        lucky_count.text = incorrect_Count.toString()

        reGame.setOnClickListener {
            this.ItemClick?.check_memo(this)
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}