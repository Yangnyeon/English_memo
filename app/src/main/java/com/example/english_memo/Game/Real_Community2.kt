package com.example.english_memo.Game

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.room.Room
import com.example.english_memo.BuildConfig
import com.example.english_memo.NaverAPI
import com.example.english_memo.R
import com.example.english_memo.ResultTransferPapago
import com.example.english_memo.Room_memo.*
import kotlinx.android.synthetic.main.activity_game_word.*
import kotlinx.android.synthetic.main.activity_real_community2.*
import kotlinx.android.synthetic.main.activity_translate.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class Real_Community2 : AppCompatActivity() {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel


    var items123 = ArrayList<UserEntity>()


    var count_number2: Int = 1

    var correct_number2: Int = 0

    val CLIENT_ID = BuildConfig.Naver_id
    val CLIENT_SECRET = BuildConfig.Naver_password
    val BASE_URL_NAVER_API = BuildConfig.BASE_URL_NAVER_API


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_community2)


        //

        Problem_produce()

        //


        //

        correct_input.setOnClickListener {
            Translate123()

            count_number2++
            count2.text = "$count_number2 /"
            if (count_number2 == 10) {
                val builder = AlertDialog.Builder(this)
                val tvName = TextView(this)
                tvName.text = "$correct_number2 문제 맞으셧습니다!"

                val mLayout = LinearLayout(this)
                mLayout.orientation = LinearLayout.VERTICAL
                mLayout.setPadding(16)
                mLayout.addView(tvName)
                builder.setView(mLayout)

                builder.setTitle("게임 결과")
                builder.setPositiveButton("다시하기") { dialog, which ->
                    count_number2 = 0

                    correct_number2 = 0
                    Problem_produce()
                    //Answer_produce()
                    correct_input.performClick()
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

            var userdao: UserDao

            val userList = database?.userDao()?.getUserAll()

            var random = Random

            val num = userList?.size?.let { random.nextInt(it) }

            var arr: ArrayList<String> = ArrayList()
            for (i in userList!!) {
                // haha2.append(i.contents)
                arr.add(i.title.toString())
            }

            english_word.text = arr[num!!]
        }

        fun Translate123() {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_NAVER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create(NaverAPI::class.java)
            val callPostTransferPapago = api.getSearchNews(
                CLIENT_ID, CLIENT_SECRET,
                "en", "ko", english_word.text.toString()
            )

            callPostTransferPapago.enqueue(object : Callback<ResultTransferPapago> {
                override fun onResponse(
                    call: Call<ResultTransferPapago>,
                    response: Response<ResultTransferPapago>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body();
                        body?.let {
                            english_word_correct.text = it.message.result.translatedText.toString()
                            if (word_edit.text.toString()
                                    .equals(english_word_correct.text.toString())
                            ) {
                                Toast.makeText(this@Real_Community2, "정답입니다!", Toast.LENGTH_SHORT)
                                    .show()
                                correct_number2++
                            } else {
                                Toast.makeText(this@Real_Community2, "오답입니다..", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            Problem_produce()
                        }
                    }
                    Log.d(ContentValues.TAG, "성공 : ${response.raw()}")
                    //textView.text = "성공 : ${response.raw()}"
                }

                override fun onFailure(call: Call<ResultTransferPapago>, t: Throwable) {
                    //Log.d(TAG, "실패 : $t")
                    textView.text = "실패 : $t"
                }
            })
        }
    }