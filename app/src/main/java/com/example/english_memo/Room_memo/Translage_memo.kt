package com.example.english_memo.Room_memo

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.english_memo.R
import com.example.english_memo.rxjavaTranning.English
import com.example.english_memo.rxjavaTranning.English_Factory
import com.example.english_memo.rxjavaTranning.OnItemClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_bar_activitity.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.view.*
import kotlinx.android.synthetic.main.activity_translage_memo.*
import kotlinx.android.synthetic.main.activity_translate.*
import kotlinx.android.synthetic.main.note_item.*
import kotlinx.android.synthetic.main.note_item.view.*

class Translage_memo : AppCompatActivity(), RecyclerViewAdapter.RowClickListener, OnItemClick {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel

    companion object{
        private val user = Firebase.auth.currentUser


        private lateinit var auth: FirebaseAuth
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translage_memo)


        viewModel = ViewModelProviders.of(this@Translage_memo)[MainActivityViewModel::class.java]


        viewModel.getAllUsersObservers().observe(this, Observer {
/*            recyclerViewAdapter?.setData(it)
            recyclerViewAdapter?.notifyDataSetChanged()
            */
            val mAdapter = RecyclerViewAdapter(this@Translage_memo,
                it as ArrayList<UserEntity>, this)
            word_RecyclerView.adapter = mAdapter
            word_RecyclerView.layoutManager = LinearLayoutManager(this)

            mAdapter.setList(it)
            Log.d("첫번째 확인",it.toString())
            mAdapter.notifyDataSetChanged()

        })



       /* contents_visible.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                word_RecyclerView.item_title.visibility = View.INVISIBLE
            } else {
                word_RecyclerView.item_title.visibility = View.VISIBLE
            }
        }
*/
        contents_visible.setOnCheckedChangeListener { compoundButton, isChecked ->
            for (i in 0 until word_RecyclerView.childCount) {
                val viewHolder = word_RecyclerView.getChildViewHolder(word_RecyclerView.getChildAt(i)) as RecyclerViewAdapter.MyViewHolder
                if (isChecked) {
                    viewHolder.item_title.visibility = View.INVISIBLE
                } else {
                    viewHolder.item_title.visibility = View.VISIBLE
                }
            }
        }



        name_visible.setOnCheckedChangeListener { compoundButton, isChecked ->
            for (i in 0 until word_RecyclerView.childCount) {
                val viewHolder = word_RecyclerView.getChildViewHolder(word_RecyclerView.getChildAt(i)) as RecyclerViewAdapter.MyViewHolder
                if (isChecked) {
                    viewHolder.item_word.visibility = View.INVISIBLE
                } else {
                    viewHolder.item_word.visibility = View.VISIBLE
                }
            }
        }

        setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    }

    override fun onItemClickupdateListener(user: UserEntity) {
        Toast.makeText(this,user.title,Toast.LENGTH_SHORT).show()
    }

    override fun onItemvisible(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteTodo(English: English) {
        TODO("Not yet implemented")
    }

    override fun check_memo(dialog: Dialog) {
        TODO("Not yet implemented")
    }

}