package com.example.english_memo.Community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.english_memo.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_fireabase_community.*
import kotlinx.android.synthetic.main.activity_firebase_tranning2.*
import kotlinx.android.synthetic.main.activity_translage_memo.*

class FirebaseTranning_2 : AppCompatActivity() {

    private lateinit var adapter:ListAdapter
    private val viewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }

   var count : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_tranning2)



        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeLayout)

        swipeRefreshLayout.performClick()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }

        adapter = ListAdapter(this)



        val recyclerView : RecyclerView = findViewById(R.id.recyclerView123)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        observerData()




//

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("User").push()


        put123.setOnClickListener {
            myRef.child("name").setValue(edt1.text.toString())
            myRef.child("region").setValue(edt2.text.toString())

            onRestart()
            //myRef.push().child("name").child("region").setValue(edt1.text.toString(),edt2.text.toString())
            //myRef.child(count.toString()).child("region").setValue(edt2.text.toString())
        }




        //


    }

   fun observerData(){
        viewModel.fetchData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
       })
   }


  //  private fun loadData() {
   //     val pref = getSharedPreferences("pref1", 0)
  //      count = pref.getInt("count1", 0)
 //   }


  //  private fun saveData() {
   //     val pref = getSharedPreferences("pref1", 0)
  //      val edit = pref.edit() // 수정모드
  //      edit.putInt("count1", count)
  //      edit.apply() //저장완료
  //  }


   // override fun onDestroy() {
   //     super.onDestroy()

  //      saveData() //저장
  //  }



}