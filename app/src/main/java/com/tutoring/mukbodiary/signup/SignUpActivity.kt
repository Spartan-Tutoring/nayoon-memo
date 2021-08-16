package com.tutoring.mukbodiary.signup

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.tutoring.mukbodiary.BaseActivity
import com.tutoring.mukbodiary.R
import com.tutoring.mukbodiary.data.entities.User
import com.tutoring.mukbodiary.data.db.UserDB

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val idEt: EditText = findViewById(R.id.signup_id_et)
        val pwEt: EditText = findViewById(R.id.signup_pw_et)
        val pwcheckEt: EditText = findViewById(R.id.signup_pwcheck_et)
        val nameEt: EditText = findViewById(R.id.signup_name_et)

        val signupBtn: Button = findViewById(R.id.signup_signup_btn)

        signupBtn.setOnClickListener {
            signUp(idEt.text.toString(), pwEt.text.toString(), pwcheckEt.text.toString(), nameEt.text.toString())
        }
    }

    private fun signUp(id: String, pw: String, pwcheck: String, name: String) {
        if(id.isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if(pw.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if(pwcheck.isEmpty()) {
            Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if(name.isEmpty()) {
            Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if(pw != pwcheck) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val userDB: UserDB = Room.databaseBuilder(this, UserDB::class.java, "user-db").allowMainThreadQueries().build()
        val alreadyUser = userDB.userDao().getUserById(id)

        if(alreadyUser === null) { //유저 아이디가 없으면 회원가입 진행
            val user = User(id = id, pw = pw, name = name)
            userDB.userDao().insertUser(user)

            val users = userDB.userDao().getUsers()
            for(user in users) {
                Log.d(
                    "user-db",
                    "idx: ${user.idx}, userId: ${user.id}, userPw: ${user.pw}, userName: ${user.name}"
                )
            }
            showDialog("가입 완료")

            //Toast.makeText(this, "가입완료", Toast.LENGTH_SHORT).show()
        } else { //유저 아이디가 이미 존재하면 가입 불가
            Toast.makeText(this, "이미 존재하는 회원입니다." +
                    "아이디를 다시 입력해주세요.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCheckClicked() {
        super.onCheckClicked()
        finish()
    }

    override fun onRestart() {
        super.onRestart()

        val idEt: EditText = findViewById(R.id.signup_id_et)
        val pwEt: EditText = findViewById(R.id.signup_pw_et)
        val pwcheckEt: EditText = findViewById(R.id.signup_pwcheck_et)
        val nameEt: EditText = findViewById(R.id.signup_name_et)

        idEt.setText("")
        pwEt.setText("")
        pwcheckEt.setText("")
        nameEt.setText("")
    }
}