package com.example.english_memo.Game

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.setPadding
import com.example.english_memo.MainActivity
import com.example.english_memo.R
import com.example.english_memo.Room_memo.Translage_memo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.view.*
import kotlinx.android.synthetic.main.game_item.*
import kotlin.system.exitProcess

class Game_Activity : AppCompatActivity() {

    lateinit var profileAdapter: Game_Adapter
    val profiles = mutableListOf<Profiles>()

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initRecycler()

        go_Dictaion.setOnClickListener {
            //startActivity(Intent(this, Real_Community2::class.java))
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

        setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        main_layout_toolbar.log_out.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val tvName = TextView(this)
            tvName.text = "\n\n로그아웃 하시겟습니까?"

            val mLayout = LinearLayout(this)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(tvName)
            builder.setView(mLayout)

            builder.setPositiveButton("확인") { dialog, which ->

                auth = FirebaseAuth.getInstance()

                Toast.makeText(this@Game_Activity, "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show()
                auth.signOut()
                //println(userProfile)
                ActivityCompat.finishAffinity(this@Game_Activity)
                exitProcess(0)
                finish()
            }
            builder.setNegativeButton("취소") { dialog, which ->

            }
            builder.show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}