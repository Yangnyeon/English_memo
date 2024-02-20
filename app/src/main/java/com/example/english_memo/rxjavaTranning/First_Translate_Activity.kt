package com.example.english_memo.rxjavaTranning

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.english_memo.BuildConfig
import com.example.english_memo.NaverAPI
import com.example.english_memo.ResultTransferPapago
import com.example.english_memo.Room_memo.UserEntity
import com.example.english_memo.databinding.ActivityFirstTranslateBinding
import kotlinx.android.synthetic.main.activity_main_bar_activitity.*
import kotlinx.android.synthetic.main.activity_translate.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class First_Translate_Activity: AppCompatActivity() {

    private lateinit var binding : ActivityFirstTranslateBinding

    private lateinit var english_adapter : English_Adapter


    private lateinit var english_viewmodel : English_ViewModel

    var english_arr = ArrayList<UserEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_first_translate)
        binding = ActivityFirstTranslateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 레트로핏 네이버

        val CLIENT_ID = BuildConfig.Naver_id
        val CLIENT_SECRET = BuildConfig.Naver_password
        val BASE_URL_NAVER_API = BuildConfig.BASE_URL_NAVER_API

        val english_repository = English_Repository(application)
        val english_viewModelFactory = English_Factory(english_repository)

        english_viewmodel = ViewModelProvider(this@First_Translate_Activity, english_viewModelFactory).get(English_ViewModel::class.java)

        binding.rotate.setOnClickListener {
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


        binding.btn.setOnClickListener {


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
                }

                override fun onFailure(call: Call<ResultTransferPapago>, t: Throwable) {
                    textView.text = "실패 : $t"
                }
            })
        }

        binding.plus.setOnClickListener {

            val currentTime : Long = System.currentTimeMillis()

            val year = SimpleDateFormat("yyyy")
            val month = SimpleDateFormat("MM")
            val day = SimpleDateFormat("dd")
            val time = SimpleDateFormat("k:mm:ss")

            val mDate: Date = Date(currentTime)

            val getYear = year.format(mDate)
            val getMonth = month.format(mDate)
            val getDay = day.format(mDate)
            val gettime = time.format(mDate)


            if(start_lag.text == "한국어") {

                /*
                val insertgogo = UserEntity(0,textView.text.toString(), et_target.text.toString())
                viewModel.insertUserInfo(insertgogo)

                 */

                english_viewmodel.insertUser(English(0,textView.text.toString(), et_target.text.toString(),Integer.parseInt(getYear), Integer.parseInt(getMonth), Integer.parseInt(getDay), gettime.toString()))

                Toast.makeText(this,"단어가 저장되었습니다.", Toast.LENGTH_SHORT).show()

            } else {

                /*
                val insertgogo = UserEntity(0,et_target.text.toString(), textView.text.toString())
                viewModel.insertUserInfo(insertgogo)

                 */

                english_viewmodel.insertUser(English(0,et_target.text.toString(),textView.text.toString(),Integer.parseInt(getYear), Integer.parseInt(getMonth), Integer.parseInt(getDay), gettime.toString()))
                Toast.makeText(this,"단어가 저장되었습니다.", Toast.LENGTH_SHORT).show()

            }

        }




        setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

}