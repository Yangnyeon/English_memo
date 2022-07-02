package com.example.english_memo

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import com.example.english_memo.Game.Game_Activity
import com.example.english_memo.Real_Community.Cloud_firestore
import com.example.english_memo.Game.Real_Community2
import com.example.english_memo.Room_memo.Translage_memo
import com.example.english_memo.Translate.Translate
import kotlinx.android.synthetic.main.activity_game_word.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Translate_gogo.setOnClickListener {
            var intent1 = Intent(this, Translate::class.java)
            startActivity(intent1)
        }

        memo_gogo.setOnClickListener {
            var intent2 = Intent(this, Translage_memo::class.java)
            startActivity(intent2)
        }


        val currentTime : Long = System.currentTimeMillis() // ms로 반환

        val day3text = SimpleDateFormat("dd") //일

        var now = LocalDate.now()

        day.text = now.dayOfWeek.toString()

        day2.text = now.month.toString()

        day3.setText(day3text.format(currentTime) + "th")

        gogo_community.setOnClickListener {
            var intent3 = Intent(this, Cloud_firestore::class.java)
            startActivity(intent3)
        }

        gogo_game.setOnClickListener {
            //var intent4 = Intent(this, Real_Community2::class.java)
            var intent4 = Intent(this, Game_Activity::class.java)
            startActivity(intent4)
        }

        diction_gogo.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val et_english = EditText(this)
            et_english.hint = "검색하고자 하는 단어를 입력하세요!"

            val mLayout = LinearLayout(this)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(et_english)
            builder.setView(mLayout)

            builder.setTitle("사전 검색하기")
            builder.setPositiveButton("검색하기") { dialog, which ->
                var diction = et_english.text.toString()
                val intent1 = Intent(Intent.ACTION_VIEW, Uri.parse("https://en.dict.naver.com/#/search?query=$diction&range=all"))
                // intent.setPackage("com.android.chrome");
                // intent.setPackage("com.android.chrome");
                startActivity(intent1)
            }
            builder.show()
        }

    }
}
