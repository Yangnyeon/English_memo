package com.example.english_memo.Game

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.room.Room
import com.example.english_memo.BuildConfig
import com.example.english_memo.BuildConfig.Naver_id
import com.example.english_memo.BuildConfig.Naver_password
import com.example.english_memo.NaverAPI
import com.example.english_memo.R
import com.example.english_memo.ResultTransferPapago
import com.example.english_memo.Room_memo.*
import kotlinx.android.synthetic.main.activity_game_word.*
import kotlinx.android.synthetic.main.activity_game_word.view.*
import kotlinx.android.synthetic.main.activity_real_community2.*
import kotlinx.android.synthetic.main.activity_translate.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class Game_word : AppCompatActivity() {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel


    var items123 = ArrayList<UserEntity>()

    val CLIENT_ID = Naver_id
    val CLIENT_SECRET = Naver_password
    val BASE_URL_NAVER_API = BuildConfig.BASE_URL_NAVER_API

    var arr_tempList : ArrayList<String> = ArrayList()

    lateinit var word123 : String

    var count_number : Int = 1

    var correct_number : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_word)

        Problem_produce()

        Answer_produce()


        correct_input2.setOnClickListener {
            click()
            count_number++
            count.text = count_number.toString() + " /"
            if(count_number == 10) {
                val builder = AlertDialog.Builder(this)
                val tvName = TextView(this)
                tvName.text = "$correct_number 문제 맞으셧습니다!"

                val mLayout = LinearLayout(this)
                mLayout.orientation = LinearLayout.VERTICAL
                mLayout.setPadding(16)
                mLayout.addView(tvName)
                builder.setView(mLayout)

                builder.setTitle("게임 결과")
                builder.setPositiveButton("다시하기") { dialog, which ->
                    count_number = 0

                    correct_number = 0
                    Problem_produce()
                    Answer_produce()
                    correct_input2.performClick()
                }
                builder.show()
            }
        }

    }



    fun Problem_produce() {
        val database: RoomAppDb = Room.databaseBuilder(
            applicationContext,
            RoomAppDb::class.java, "AppDBB"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        var userdao : UserDao

        val userList = database?.userDao()?.getUserAll()

        var random = Random

        val num = userList?.size?.let { random.nextInt(it) }

        var arr : ArrayList<String> = ArrayList()
        var arr_trans : ArrayList<String> = ArrayList()

        for(i in userList!!) {
            // haha2.append(i.contents)
            arr.add(i.title.toString())
            arr_trans.add(i.date.toString())
        }

        english_word2.text = arr[num!!]
        arr_tempList.add(arr_trans[num!!])
        word_result.text = arr_trans[num!!]

    }

    fun Answer_produce() {


        val database: RoomAppDb = Room.databaseBuilder(
            applicationContext,
            RoomAppDb::class.java, "AppDBB"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        var userdao : UserDao

        val userList = database?.userDao()?.getUserAll()

        var random = Random

        val num2 = userList?.size?.let { random.nextInt(it) }

        var arr2 : ArrayList<String> = ArrayList()


        for(i in userList!!) {
            arr2.add(i.date.toString())
        }
        while(arr_tempList.size < 4) {
            val num2 = userList?.size?.let { random.nextInt(it) }
            var arr_temp = arr2[num2!!]
            if(!arr_tempList.contains(arr_temp)) {
            arr_tempList.add(arr_temp) //데이터 베이스에 있는 영어 추가
            }
        }
        arr_tempList.shuffle()

        word1.text = arr_tempList[0]
        word2.text = arr_tempList[1]
        word3.text = arr_tempList[2]
        word4.text = arr_tempList[3]

        Card1.setOnClickListener {
            word_result2.text = word1.text.toString()
            Card1.setBackgroundColor(Color.BLUE)
            Card2.setBackgroundColor(Color.WHITE)
            Card3.setBackgroundColor(Color.WHITE)
            Card4.setBackgroundColor(Color.WHITE)
        }

        Card2.setOnClickListener {
            word_result2.text = word2.text.toString()
            Card1.setBackgroundColor(Color.WHITE)
            Card2.setBackgroundColor(Color.BLUE)
            Card3.setBackgroundColor(Color.WHITE)
            Card4.setBackgroundColor(Color.WHITE)
        }

        Card3.setOnClickListener {
            word_result2.text = word3.text.toString()
            Card1.setBackgroundColor(Color.WHITE)
            Card2.setBackgroundColor(Color.WHITE)
            Card3.setBackgroundColor(Color.BLUE)
            Card4.setBackgroundColor(Color.WHITE)
        }

        Card4.setOnClickListener {
            word_result2.text = word4.text.toString()
            Card1.setBackgroundColor(Color.WHITE)
            Card2.setBackgroundColor(Color.WHITE)
            Card3.setBackgroundColor(Color.WHITE)
            Card4.setBackgroundColor(Color.BLUE)
        }


        //arr_tempList[1]


    }

    fun click() {
        if(word_result.text.toString() == word_result2.text.toString()) {
            Toast.makeText(this@Game_word, "정답입니다!", Toast.LENGTH_SHORT).show()
            correct_number++
        } else {
            Toast.makeText(this@Game_word, "오답입니다..", Toast.LENGTH_SHORT).show()
        }
        arr_tempList.clear()
        Problem_produce()
        Answer_produce()
    }
}