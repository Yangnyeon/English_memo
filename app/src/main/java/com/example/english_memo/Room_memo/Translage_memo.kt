package com.example.english_memo.Room_memo

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.core.app.ActivityCompat
import androidx.core.view.allViews
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.example.english_memo.MainActivity
import com.example.english_memo.R
import com.example.english_memo.databinding.ActivityTranslageMemoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main_bar_activitity.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.view.*
import kotlinx.android.synthetic.main.activity_translage_memo.*
import kotlinx.android.synthetic.main.activity_translate.*
import kotlinx.android.synthetic.main.note_item.*
import kotlinx.android.synthetic.main.note_item.view.*
import kotlin.system.exitProcess

class Translage_memo : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel

    var items123 = ArrayList<UserEntity>()

    companion object{
        private var imageUri : Uri? = null
        private val fireStorage = FirebaseStorage.getInstance().reference
        private val fireDatabase = FirebaseDatabase.getInstance().reference
        private val user = Firebase.auth.currentUser
        private val uid = user?.uid.toString()


        private lateinit var auth: FirebaseAuth

        fun newInstance() : MainActivity {
            return MainActivity()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translage_memo)

        /*
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter(this@Translage_memo, items123)
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(context, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

         */

        recyclerView.layoutManager = LinearLayoutManager(this@Translage_memo)
        recyclerViewAdapter = RecyclerViewAdapter(this@Translage_memo, items123)
        recyclerView.adapter = recyclerViewAdapter

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)


        /*
        viewModel.getAllUsersObservers().observe(this, Observer {
            recyclerViewAdapter?.setList(it)
            recyclerViewAdapter?.notifyDataSetChanged()
        })

         */

        viewModel.getAllUsersObservers().observe(this, Observer {
            recyclerViewAdapter?.setData(it)
            recyclerViewAdapter?.notifyDataSetChanged()
        })



        /*
        val database: RoomAppDb = Room.databaseBuilder(
            applicationContext,
           RoomAppDb::class.java, "AppDBB"
        )
            .fallbackToDestructiveMigration()
           .allowMainThreadQueries()
           .build()


      val userList = database?.userDao()?.getUserAll()

         */

        English_Searchview.setOnQueryTextListener(searchViewTextListener)


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

        setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        main_layout_toolbar.log_out.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val tvName = TextView(this)
            tvName.text = "\n\n로그아웃 하시겟습니까?"

            val mLayout = LinearLayout(this)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(tvName)
            builder.setView(mLayout)

            builder.setPositiveButton("확인") { dialog, which ->

                auth = FirebaseAuth.getInstance()

                Toast.makeText(this@Translage_memo, "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show()
                auth.signOut()
                //println(userProfile)
                ActivityCompat.finishAffinity(this@Translage_memo)
                exitProcess(0)
                finish()
            }
            builder.setNegativeButton("취소") { dialog, which ->

            }
            builder.show()
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

    }

    override fun onItemClickupdateListener(user: UserEntity) {
        Toast.makeText(this,user.title,Toast.LENGTH_SHORT).show()
    }

    override fun onItemvisible(user: UserEntity) {
        TODO("Not yet implemented")
    }


    private var searchViewTextListener: androidx.appcompat.widget.SearchView.OnQueryTextListener =
        object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                /*
                val mAdapter = CigaretteAdapter(this@Room_Activity)
                mAdapter.filter.filter(s)
                Log.d(TAG, "SearchVies Text is changed : $s")

                 */

                searchDatabase(s)

                return true
            }
        }

    //

    @SuppressLint("NotifyDataSetChanged")
    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this, {
            recyclerViewAdapter.setData(it)
            recyclerViewAdapter.notifyDataSetChanged()
        })

    }



}