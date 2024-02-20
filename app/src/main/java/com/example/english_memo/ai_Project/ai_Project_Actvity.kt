package com.example.english_memo.ai_Project

import android.content.ContentValues
import android.graphics.Color
import android.graphics.Insets.add
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.english_memo.*
import com.example.english_memo.BuildConfig.BASE_URL_NAVER_API
import kotlinx.android.synthetic.main.activity_ai_project_actvity.*
import kotlinx.android.synthetic.main.activity_translate.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ai_Project_Actvity : AppCompatActivity() {

    private lateinit var viewModel : koGpt_ViewModel

    private var koGpt_List = ArrayList<Generation>()

    private lateinit var mAdapter: koGpt_AdaPter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ai_project_actvity)

        val CLIENT_ID = BuildConfig.Naver_id
        val CLIENT_SECRET = BuildConfig.Naver_password
        val BASE_URL_NAVER_API = BuildConfig.BASE_URL_NAVER_API


        val repository = koGpt_Repository()
        val viewModelFactory = koGpt_ViewModel_Factory(repository)

        mAdapter = koGpt_AdaPter(koGpt_List , this@ai_Project_Actvity)
        koGpt_RecyclerView.adapter = mAdapter
        koGpt_RecyclerView.layoutManager = LinearLayoutManager(this@ai_Project_Actvity)

        val loadingAnimDialog = loading_screen(this@ai_Project_Actvity)

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        commnet_button.setOnClickListener {

            loadingAnimDialog.show()

            viewModel = ViewModelProvider(this,viewModelFactory).get(koGpt_ViewModel::class.java)

            viewModel.PostKoGpt(comment_edit.text.toString())

            viewModel.myResponse.observe(this, Observer { response ->
                Log.d("확인첫번째", response.body().toString())

                if (response.isSuccessful) {
                    Log.d("확인응답", response.toString())

                    // 응답 바디를 가져옴
                    val body = response.body()

                    // body가 null이 아닌 경우에만 처리
                    body?.let { responseBody ->
                        val generations = responseBody.generations

                        // generations가 비어 있는지 확인하여 한 번만 호출되도록 처리

                        generations.forEach {
                            var chat_Send = comment_edit.text.toString()

                            var enemy_Token = it.tokens

                            koGpt_List.add(Generation(chat_Send, 999))

                            //번역과정

                            val retrofit = Retrofit.Builder()
                                .baseUrl(BuildConfig.BASE_URL_NAVER_API)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                            val api = retrofit.create(NaverAPI::class.java)
                            val callPostTransferPapago = api.getSearchNews(CLIENT_ID, CLIENT_SECRET,
                                "ko", "en", it.text)

                            callPostTransferPapago.enqueue(object : Callback<ResultTransferPapago> {
                                override fun onResponse(
                                    call: Call<ResultTransferPapago>,
                                    response: Response<ResultTransferPapago>
                                ) {
                                    if(response.isSuccessful) {
                                        val body = response.body();
                                        body?.let {
                                            koGpt_List.add(Generation(it.message.result.translatedText, enemy_Token))
                                            //상대 문장을 번역후 데이터 추가
                                            mAdapter.notifyDataSetChanged()
                                            loadingAnimDialog.dismiss()
                                         }
                                    }
                                    Log.d(ContentValues.TAG, "성공 : ${response.raw()}")
                                }

                                override fun onFailure(call: Call<ResultTransferPapago>, t: Throwable) {
                                    textView.text = "실패 : $t"
                               }
                            })

                        }


                    }
                } else {
                    // 응답이 실패한 경우 처리
                }
            })
            koGpt_RecyclerView.scrollToPosition(koGpt_List.size - 1)
        }



    }


}