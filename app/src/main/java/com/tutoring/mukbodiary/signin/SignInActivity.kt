package com.tutoring.mukbodiary.signin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.tutoring.mukbodiary.R
import com.tutoring.mukbodiary.main.MainActivity
import com.tutoring.mukbodiary.setting.SettingActivity
import com.tutoring.mukbodiary.signup.SignUpActivity
import com.tutoring.mukbodiary.data.db.UserDB

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val idEt: EditText = findViewById(R.id.signin_id_et)
        val pwEt: EditText = findViewById(R.id.signin_pw_et)

        val signInBtn: Button = findViewById(R.id.signin_signin_btn)
        val signupBtn: Button = findViewById(R.id.signin_signup_btn)

        signInBtn.setOnClickListener {

            signIn(idEt.text.toString(), pwEt.text.toString())
        }

        signupBtn.setOnClickListener {
            startSignUpActivity()
        }
    }

    override fun onRestart() {
        super.onRestart()

        val idEt: EditText = findViewById(R.id.signin_id_et)
        val pwEt: EditText = findViewById(R.id.signin_pw_et)

        idEt.setText("")
        pwEt.setText("")
    }

    private fun signIn(id: String, pw: String) {

        if(id.isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if(pw.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val userDB: UserDB = Room.databaseBuilder(this, UserDB::class.java, "user-db").allowMainThreadQueries().build()
        val user = userDB.userDao().getUser(id, pw)

        Log.d("user-db", "$user")

        user?.let {
            val spf : SharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
            val editor = spf.edit()

            editor.putInt("token", user.idx)
            editor.putString("name", user.name)
            editor.apply()

            startMainActivity()

            return
        }

        Toast.makeText(this, "일치하는 회원정보가 없습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun startSignUpActivity() { //signUp screen으로 넘어가는 fun
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun startMainActivity() { //main screen으로 넘어가는 fun
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun startSettingActivity(name: String) { //setting screen으로 넘어가는 fun
        val intent = Intent(this, SettingActivity::class.java)
        intent.putExtra("user-name",name)
        startActivity(intent)

        finish()
    }
}