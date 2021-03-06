package com.example.english_memo.Translate

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.english_memo.BuildConfig.*
import com.example.english_memo.NaverAPI
import com.example.english_memo.R
import com.example.english_memo.ResultTransferPapago
import com.example.english_memo.Room_memo.MainActivityViewModel
import com.example.english_memo.Room_memo.RecyclerViewAdapter
import com.example.english_memo.Room_memo.UserEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_translage_memo.*
import kotlinx.android.synthetic.main.activity_translate.*
import kotlinx.android.synthetic.main.activity_translate.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Translate : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel


    var items123 = ArrayList<UserEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        // 레트로핏 네이버

        val CLIENT_ID = Naver_id
        val CLIENT_SECRET = Naver_password
        val BASE_URL_NAVER_API = BASE_URL_NAVER_API


        //

        // 리사이클러뷰

        recyclerView12.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter(this@Translate, items123)//이거 알트 엔터후 4번째꺼
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(context, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getAllUsersObservers().observe(this, androidx.lifecycle.Observer {
            recyclerViewAdapter?.setListData(ArrayList(it))
            recyclerViewAdapter?.notifyDataSetChanged()
        })

        //

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
                    //textView.text = "성공 : ${response.raw()}"
                }

                override fun onFailure(call: Call<ResultTransferPapago>, t: Throwable) {
                    //Log.d(TAG, "실패 : $t")
                    textView.text = "실패 : $t"
                }
            })
        }

        plus.setOnClickListener {
            val insertgogo = UserEntity(0,textView.text.toString(), et_target.text.toString())
            viewModel.insertUserInfo(insertgogo)
            Toast.makeText(this,"단어가 저장되었습니다.", Toast.LENGTH_SHORT).show()
        }


        // 레트로핏 네이버

        back.setOnClickListener {
            finish()
        }






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
}