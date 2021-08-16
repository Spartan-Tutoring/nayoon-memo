package com.tutoring.mukbodiary.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tutoring.mukbodiary.R
import com.tutoring.mukbodiary.main.MainActivity
import com.tutoring.mukbodiary.setting.SettingActivity
import com.tutoring.mukbodiary.signin.SignInActivity
import java.util.*
import kotlin.concurrent.schedule
class SplashActivity : AppCompatActivity() { //상속

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        Timer().schedule(3000) {
            autoLogin()
        }
    }

    private fun autoLogin() {
        val spf : SharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
        val token = spf.getInt("token", 0)

        if(token != 0) {
            startMainActivity()
            return
        }

        startSignInActivity()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun startSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun startSettingActivity() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }


}