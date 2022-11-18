package com.example.english_memo.Translate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.english_memo.R
import com.example.english_memo.Room_memo.MainActivityViewModel
import com.example.english_memo.Room_memo.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_calendar_english.*
import kotlinx.android.synthetic.main.activity_translage_memo.*
import java.text.SimpleDateFormat
import java.util.*

class Calendar_English : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_english)

        val currentTime : Long = System.currentTimeMillis()

        val year = SimpleDateFormat("yyyy")
        val month = SimpleDateFormat("MM")
        val day = SimpleDateFormat("dd")
        val time = SimpleDateFormat("k:mm:ss")

        val mDate: Date = Date(currentTime)

        /*

        val getYear = year.format(mDate)
        val getMonth = month.format(mDate)
        val getDay = day.format(mDate)
        val gettime = time.format(mDate)

        calendarRecyclerview.layoutManager = LinearLayoutManager(this@Calendar_English)
        recyclerViewAdapter = RecyclerViewAdapter(this@Calendar_English, items123)
        recyclerView.adapter = recyclerViewAdapter

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

         */


    }
}