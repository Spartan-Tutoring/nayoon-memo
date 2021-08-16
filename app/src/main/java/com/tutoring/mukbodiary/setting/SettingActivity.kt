package com.tutoring.mukbodiary.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.tutoring.mukbodiary.BaseActivity
import com.tutoring.mukbodiary.R
import com.tutoring.mukbodiary.data.db.UserDB
import com.tutoring.mukbodiary.main.MainActivity
import com.tutoring.mukbodiary.signin.SignInActivity

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val nameEt: EditText = findViewById(R.id.setting_username_et) //유저 이름 정보
        val editBtn: ImageButton = findViewById(R.id.setting_edit_ib)//유저 이름 수정 버튼
        val logoutBtn: Button = findViewById(R.id.setting_logout_btn)//로그아웃 버튼
        val profileImg: ImageView = findViewById(R.id.setting_profile_iv)//프로필 사진

        val memoCnt: TextView = findViewById(R.id.setting_memoCnt_tv)
        val bookmarkCnt: TextView = findViewById(R.id.setting_bookMarkCnt_tv)

        //name 정보 가져와서 유저 이름 정보 TextView에 넣기
        val userDB: UserDB = Room.databaseBuilder(this, UserDB::class.java, "user-db").allowMainThreadQueries().build()
        val user =userDB.userDao().getUserByIdx(getUserIdx())

        nameEt.setText(user!!.name)

        profileImg.setOnClickListener {
            
        }

        //유저 이름 수정 기능
        editBtn.setOnClickListener {
            val name = nameEt.text.toString()
            nameEt.setText(name)
            val userDB: UserDB = Room.databaseBuilder(this, UserDB::class.java, "user-db").allowMainThreadQueries().build()
            userDB.userDao().updateUserName(getUserIdx(), name)

            showDialog("이름이 변경되었습니다.")
        }

        //카운트 기능
        val spf : SharedPreferences = getSharedPreferences("memo", AppCompatActivity.MODE_PRIVATE)
        val total = spf.getInt("memoCnt", 0).toString()
        val marked =  spf.getInt("bookmarkCnt", 0).toString()
        memoCnt.setText("총 " + total + "개의 메모")
//        bookmarkCnt.setText("총 " + marked + "개의 즐겨찾기")

        //로그아웃 기능
        logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun getUserIdx(): Int {
        val spf : SharedPreferences = getSharedPreferences("memo", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("token", 0)
    }

    private fun logout() {
        val spf: SharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.remove("token")
        editor.apply()

        startSignInActivity()
    }

    override fun onCheckClicked() {
        super.onCheckClicked()
        finish()
    }

    private fun startSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}