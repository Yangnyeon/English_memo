package com.example.english_memo.Calendar

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.example.english_memo.R
import com.example.english_memo.databinding.ActivityCalendarEnglishBinding
import com.example.english_memo.databinding.ActivitySecondTranslateBinding
import com.example.english_memo.rxjavaTranning.*
import kotlinx.android.synthetic.main.activity_calendar_view.*
import java.text.SimpleDateFormat
import java.util.*

class Calendar_View : AppCompatActivity(), OnItemClick {

    private lateinit var binding : ActivityCalendarEnglishBinding

    private lateinit var viewModel : English_ViewModel

    lateinit var adapter: English_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarEnglishBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val repository = English_Repository(application)
        val viewModelFactory = English_Factory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory)[English_ViewModel::class.java]

        val mAdapter = English_Adapter(this@Calendar_View)
        binding.calendarRecyclerview.adapter = mAdapter
        binding.calendarRecyclerview.layoutManager =
            LinearLayoutManager(this@Calendar_View)

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

        binding.calendarDateText.text = Integer.parseInt(getYear).toString() + "/" + Integer.parseInt(getMonth) + "/" + Integer.parseInt(getDay)


        binding.calendarView.selectionManager = SingleSelectionManager(OnDaySelectedListener {
            val days: List<Calendar> = calendarView.getSelectedDates()
            var result = ""
            for (i in days.indices) {
                /*
                val week: String = SimpleDateFormat("EE").format(calendar.time)
                val day_full =
                    year.toString() + "년 " + (month + 1) + "월 " + day + "일 " + week + "요일"
                result +=  "$day_full"

                 */
                val calendar = days[i]
                val day = calendar[Calendar.DAY_OF_MONTH]
                val month = calendar[Calendar.MONTH]
                val year = calendar[Calendar.YEAR]

                binding.calendarDateText.text = "${year}/${month + 1}/${day}"

                viewModel.readDateData(year,month + 1,day).observe(this@Calendar_View, androidx.lifecycle.Observer {
                    mAdapter.setList(it)
                    mAdapter.notifyDataSetChanged()
                })

            }
        })


    }

    override fun deleteTodo(English: English) {
        viewModel.delete(English)
    }

    override fun check_memo(dialog: Dialog) {
        TODO("Not yet implemented")
    }
}