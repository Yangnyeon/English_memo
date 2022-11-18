package com.example.english_memo.rxjavaTranning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.english_memo.databinding.ActivitySecondTranslateBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*

class Second_Translate_Activity : AppCompatActivity(), OnItemClick  {

    private lateinit var binding : ActivitySecondTranslateBinding

    private lateinit var viewModel : English_ViewModel
    lateinit var adapter: English_Adapter

    var english_list: ArrayList<English> = java.util.ArrayList<English>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondTranslateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*
        initRecyclerView()

         */


        val repository = English_Repository(application)
        val viewModelFactory = English_Factory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory)[English_ViewModel::class.java]

        val mAdapter = English_Adapter(this)
        binding.englishRecyclerView.adapter = mAdapter
        binding.englishRecyclerView.layoutManager =
            LinearLayoutManager(this@Second_Translate_Activity)


        viewModel.getAll().observe(this, androidx.lifecycle.Observer {
            mAdapter.setList(it)
            mAdapter.notifyDataSetChanged()
        })


    }

    private fun setAdapter(consult_List: Observable<List<English>>) {
        //val mAdapter = English_Adapter(this, consult_List as ArrayList<English>)
        //binding.englishRecyclerView.adapter = mAdapter
        binding.englishRecyclerView.layoutManager = LinearLayoutManager(this@Second_Translate_Activity)
    }


    override fun deleteTodo(English: English) {
        viewModel.delete(English)
    }

    /*
    private fun initRecyclerView(){
        binding.englishRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = English_Adapter(this)
        binding.englishRecyclerView.adapter = adapter
    }

     */
}