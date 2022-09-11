package com.example.english_memo

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_loading_screen.*

class loading_screen(context: Context): Dialog(context) {

    lateinit var turnAround : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        turnAround = AnimationUtils.loadAnimation(context, R.anim.turn_around)

        loading_image.startAnimation(turnAround)

    }
}