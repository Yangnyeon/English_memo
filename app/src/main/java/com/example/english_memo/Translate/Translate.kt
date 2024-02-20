package com.example.english_memo.Translate

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.example.english_memo.BuildConfig.*
import com.example.english_memo.MainActivity
import com.example.english_memo.NaverAPI
import com.example.english_memo.R
import com.example.english_memo.ResultTransferPapago
import com.example.english_memo.Room_memo.MainActivityViewModel
import com.example.english_memo.Room_memo.RecyclerViewAdapter
import com.example.english_memo.Room_memo.UserEntity

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.view.*
import kotlinx.android.synthetic.main.activity_translage_memo.*
import kotlinx.android.synthetic.main.activity_translate.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import com.example.english_memo.Room_memo.Translage_memo
import kotlinx.android.synthetic.main.activity_first_translate.*
import kotlinx.android.synthetic.main.activity_translate.btn
import kotlinx.android.synthetic.main.activity_translate.end_lag
import kotlinx.android.synthetic.main.activity_translate.end_reallag
import kotlinx.android.synthetic.main.activity_translate.et_target
import kotlinx.android.synthetic.main.activity_translate.plus
import kotlinx.android.synthetic.main.activity_translate.rotate
import kotlinx.android.synthetic.main.activity_translate.start_lag
import kotlinx.android.synthetic.main.activity_translate.start_reallag
import kotlinx.android.synthetic.main.activity_translate.textView
import java.util.*
import kotlin.system.exitProcess


class Translate : AppCompatActivity(), RecyclerViewAdapter.RowClickListener, TextToSpeech.OnInitListener  {

    lateinit var viewModel: MainActivityViewModel

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        // 레트로핏 네이버

        val CLIENT_ID = Naver_id
        val CLIENT_SECRET = Naver_password
        val BASE_URL_NAVER_API = BASE_URL_NAVER_API


        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]


        rotate.setOnClickListener {
            if(start_lag.text == "한국어") {
                start_lag.text = "영어"
                end_lag.text = "한국어"
                start_reallag.text = "en"
                end_reallag.text = "ko"
            } else {
                start_lag.text = "한국어"
                end_lag.text = "영어"
                start_reallag.text = "ko"
                end_reallag.text = "en"
            }

        }


        btn.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_NAVER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create(NaverAPI::class.java)
            val callPostTransferPapago = api.getSearchNews(CLIENT_ID, CLIENT_SECRET,
                start_reallag.text.toString(), end_reallag.text.toString(), et_target.text.toString())


            callPostTransferPapago.enqueue(object : Callback<ResultTransferPapago> {
                override fun onResponse(
                    call: Call<ResultTransferPapago>,
                    response: Response<ResultTransferPapago>
                ) {
                    if(response.isSuccessful) {
                        val body = response.body();
                        body?.let {
                            textView.text = it.message.result.translatedText.toString()
                        }
                    }
                    Log.d(ContentValues.TAG, "성공 : ${response.raw()}")
                }

                override fun onFailure(call: Call<ResultTransferPapago>, t: Throwable) {
                    textView.text = "실패 : $t"
                }
            })
        }

        plus.setOnClickListener {
            if(start_lag.text == "한국어") {


                val insertgogo = UserEntity(0,textView.text.toString(), et_target.text.toString())
                viewModel.insertUserInfo(insertgogo)


                Toast.makeText(this,"단어가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val insertgogo = UserEntity(0,et_target.text.toString(), textView.text.toString())
                viewModel.insertUserInfo(insertgogo)
                Toast.makeText(this,"단어가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textToSpeech = TextToSpeech(this, this)

        english_Sound.setOnClickListener {
            speak(textView.text.toString())
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

    override fun onDeleteUserClickListener(user: UserEntity) {
        viewModel.deleteUserInfo(user)
    }

    override fun onItemClickListener(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun onItemClickupdateListener(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun onItemvisible(user: UserEntity) {
        TODO("Not yet implemented")
    }

    private fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language for TextToSpeech
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or not supported, handle accordingly
            }
        } else {
            // Initialization failed, handle accordingly
        }
    }
}