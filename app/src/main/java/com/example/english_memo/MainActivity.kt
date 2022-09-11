package com.example.english_memo

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.english_memo.Game.Game_Activity
import com.example.english_memo.Real_Community.Cloud_firestore
import com.example.english_memo.Game.Real_Community2
import com.example.english_memo.Login.Friend
import com.example.english_memo.Room_memo.MainActivityViewModel
import com.example.english_memo.Room_memo.Translage_memo
import com.example.english_memo.Translate.Translate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_game_word.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    companion object{
        private var imageUri : Uri? = null
        private val fireStorage = FirebaseStorage.getInstance().reference
        private val fireDatabase = FirebaseDatabase.getInstance().reference
        private val user = Firebase.auth.currentUser
        private val uid = user?.uid.toString()


        private lateinit var auth: FirebaseAuth

        fun newInstance() : MainActivity {
            return MainActivity()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Translate_gogo.setOnClickListener {
            var intent1 = Intent(this, Translate::class.java)
            startActivity(intent1)
        }


        try {
            memo_gogo.setOnClickListener {
                var intent2 = Intent(this, Translage_memo::class.java)
                startActivity(intent2)
            }
        } catch (e: Exception) {

        }


        val currentTime: Long = System.currentTimeMillis() // ms로 반환

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
                val intent1 = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://en.dict.naver.com/#/search?query=$diction&range=all")
                )
                // intent.setPackage("com.android.chrome");
                // intent.setPackage("com.android.chrome");
                startActivity(intent1)
            }
            builder.show()
        }

        CoroutineScope(Dispatchers.Main).launch {

            fireDatabase.child("users").child(uid).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userProfile = snapshot.getValue<Friend>()
                    Welcome.text = userProfile?.name + "님\n" + "환영합니다!"
                }
            })

            setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
            supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
            //supportActionBar?.setDisplayHomeAsUpEnabled(true) 뒤로가기
            main_layout_toolbar.log_out.setOnClickListener {
                try {
                    auth.signOut()
                }catch (e : Exception) {
                    Toast.makeText(this@MainActivity, "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show()
                    ActivityCompat.finishAffinity(this@MainActivity)
                    exitProcess(0)
                    finish()
                }
                Toast.makeText(this@MainActivity, "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show()
                ActivityCompat.finishAffinity(this@MainActivity)
                exitProcess(0)
                finish()

            }
            bar_title.setOnClickListener {
                Toast.makeText(this@MainActivity, "ㅇㅇ", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
