package com.example.english_memo.Room_memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.core.view.allViews
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.example.english_memo.R
import kotlinx.android.synthetic.main.activity_translage_memo.*
import kotlinx.android.synthetic.main.activity_translate.*
import kotlinx.android.synthetic.main.activity_translate.back
import kotlinx.android.synthetic.main.note_item.*
import kotlinx.android.synthetic.main.note_item.view.*

class Translage_memo : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel

    var items123 = ArrayList<UserEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translage_memo)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter(this@Translage_memo, items123)
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(context, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getAllUsersObservers().observe(this, androidx.lifecycle.Observer {
            recyclerViewAdapter?.setListData(ArrayList(it))
            recyclerViewAdapter?.notifyDataSetChanged()
        })

        back.setOnClickListener {
            finish()
        }

        val database: RoomAppDb = Room.databaseBuilder(
            applicationContext,
           RoomAppDb::class.java, "AppDBB"
        )
            .fallbackToDestructiveMigration()
           .allowMainThreadQueries()
           .build()


      val userList = database?.userDao()?.getUserAll()


        contents_visible.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                recyclerView.item_title.visibility = View.INVISIBLE
            } else {
                recyclerView.item_title.visibility = View.VISIBLE
            }
        }

        name_visible.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                item_date.visibility = View.INVISIBLE
            } else {
                item_date.visibility = View.VISIBLE
            }
        }







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

}