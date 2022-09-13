package com.example.english_memo.Login

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.english_memo.MainActivity
import com.example.english_memo.R
import com.example.english_memo.databinding.ActivityLoginBinding
import com.example.english_memo.loading_screen
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main_bar_activitity.*
import kotlinx.android.synthetic.main.activity_main_bar_activitity.view.*
import java.util.*

class Login_Activity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth =  FirebaseAuth.getInstance()

        val email = findViewById<TextInputEditText>(R.id.et_login_id)
        val password = findViewById<TextInputEditText>(R.id.et_login_password)


        //자동 로그인


        var currentUser = auth?.currentUser

        if (currentUser == null) {


        }else{
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    val intent: Intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 1000)

        }



            binding.profileButton.setOnClickListener{

                if(email.text!!.isEmpty() && password.text!!.isEmpty()) {
                    Toast.makeText(this, "아이디와 비밀번호를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()
                    Log.d("Email", "$email, $password")
                    email.setText("")
                    password.setText("")
                }
                else{
                    signIn(email.text.toString(), password.text.toString())
                }
            }



        binding.btnRegistration.setOnClickListener {
            val intent = Intent(this, Registration_Activity::class.java)
            startActivity(intent)
        }



    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        val intentMain = Intent(this@Login_Activity, MainActivity::class.java)

        val loadingAnimDialog = loading_screen(this@Login_Activity)

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //finish()
                    //startActivity(intentMain)
                    moveMainPage(auth?.currentUser)
                    loadingAnimDialog.dismiss()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "정확한 아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    loadingAnimDialog.dismiss()
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {

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

    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}