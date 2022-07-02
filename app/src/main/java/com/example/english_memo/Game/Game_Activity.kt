package com.example.english_memo.Game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.english_memo.R
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.game_item.*

class Game_Activity : AppCompatActivity() {

    lateinit var profileAdapter: Game_Adapter
    val profiles = mutableListOf<Profiles>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initRecycler()

        go_Dictaion.setOnClickListener {
            startActivity(Intent(this, Real_Community2::class.java))
        }

        go_word.setOnClickListener {
            startActivity(Intent(this, Game_word::class.java))
        }
    }

    private fun initRecycler() {
        profileAdapter = Game_Adapter(this)
        //re.adapter = profileAdapter


        profiles.apply {
            add(Profiles(Game_iamge = R.drawable.restaurant_image, Game_imagetext = "영어 받아쓰기", Game_word::class.java.toString()))
            add(Profiles(Game_iamge = R.drawable.restaurant_image, Game_imagetext = "영어 단어게임", Real_Community2::class.java.toString()))
            add(Profiles(Game_iamge = R.drawable.restaurant_image, Game_imagetext = "그외 게임", Real_Community2::class.java.toString()))


            profileAdapter.profileList = profiles as ArrayList<Profiles>
            profileAdapter.notifyDataSetChanged()

        }

    }
}