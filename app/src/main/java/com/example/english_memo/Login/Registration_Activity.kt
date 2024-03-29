package com.example.english_memo.Login

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.example.english_memo.MainActivity
import com.example.english_memo.R
import com.example.english_memo.loading_screen
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_registration.*


private lateinit var auth: FirebaseAuth
lateinit var database: DatabaseReference

class Registration_Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = Firebase.auth
        database = Firebase.database.reference

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        val email = et_registration_id.text
        val password = et_registration_password.text
        var reapssword = et_registration_repassword.text
        val name = findViewById<TextInputEditText>(R.id.et_registration_name).text
        val button = findViewById<CardView>(R.id.btn_registration)


        button.setOnClickListener {

            val loadingAnimDialog = loading_screen(this@Registration_Activity)

            loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            loadingAnimDialog.show()

            if(email!!.isEmpty() && password!!.isEmpty() && name!!.isEmpty())  {
                Toast.makeText(this, "아이디와 비밀번호 를 제대로 입력해주세요!", Toast.LENGTH_SHORT).show()
                Log.d("Email", "$email, $password")
            }
            if(!password!!.toString().equals(reapssword.toString())) {
                Toast.makeText(this, "비밀번호 재확인이 일치하지않습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = Firebase.auth.currentUser
                            val userId = user?.uid
                            val userIdSt = userId.toString()
                            val friend = Friend(
                                email.toString(),
                                name.toString(),
                                userIdSt
                            )
                            database.child("users").child(userId.toString())
                                .setValue(friend)

                            loadingAnimDialog.dismiss()
                            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun reload() {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}