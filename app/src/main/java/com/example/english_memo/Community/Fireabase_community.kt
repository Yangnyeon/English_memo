package com.example.english_memo.Community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.english_memo.R
import com.example.english_memo.Real_Community.Cloud_firestore
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_fireabase_community.*
import kotlinx.android.synthetic.main.activity_translate.*

class Fireabase_community : AppCompatActivity() {

    var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fireabase_community)

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("message")
        //myRef.setValue("안녕 반가워!")//mRootRef.child




            //Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var text = snapshot?.value
                    textview123.text = "$text"

                    //val value = snapshot?.value
                    // textview123.text = "$value"
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        button.setOnClickListener {
            count++
            myRef.child(count.toString()).child("name").setValue(edittext.text.toString())
        }

        re_gogo.setOnClickListener {
            var intent4 = Intent(this, FirebaseTranning_2::class.java)
            startActivity(intent4)
        }

        fi_gogo.setOnClickListener {
            var intent5 = Intent(this, Cloud_firestore::class.java)
            startActivity(intent5)
        }



    }
}