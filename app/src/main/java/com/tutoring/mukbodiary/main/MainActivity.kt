package com.tutoring.mukbodiary.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.tutoring.mukbodiary.BaseActivity
import com.tutoring.mukbodiary.R
import com.tutoring.mukbodiary.data.db.MemoDB
import com.tutoring.mukbodiary.data.entities.Memo
import com.tutoring.mukbodiary.databinding.ActivityMainBinding
import com.tutoring.mukbodiary.main.view.ViewActivity
import com.tutoring.mukbodiary.setting.SettingActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: MainRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainPostBtn.setOnClickListener(this)

        //리스트<->지도
        binding.mainModeBtn.setOnClickListener {
            Toast.makeText(this, "지도모드", Toast.LENGTH_SHORT).show()
        }

        //프로필
        binding.mainProfileIv.setOnClickListener {
            startSettingActivity()
        }

        //모아보기
        binding.mainViewBtn.setOnClickListener {
            startViewActivity()
        }

        //메모작성
        binding.mainPostBtn.setOnClickListener {
            startPostActivity()
        }

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        val token = getWriterIdx()

        if(token != 0) {
            getMemos(token)
        }
    }

    private fun initRecyclerView() {

        //layout manager 연결
        val lm = LinearLayoutManager(this)
        binding.mainRecyclerview.layoutManager = lm

        //adapter 연결
        rvAdapter = MainRvAdapter(this)

        //item 클릭하면 PostActivity로 이동
        rvAdapter.setItemClickListner(object: MainRvAdapter.ItemClickListner {
            override fun onClick(view: View, position: Int, memo: Memo) {
                startPostActivity(memo)
            }
        })

        binding.mainRecyclerview.adapter = rvAdapter
    }

    private fun getMemos(writer: Int) {
        val db: MemoDB = Room.databaseBuilder(this, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
        val list = ArrayList(db.memoDao().getMemoByWriter(writer))

        rvAdapter.addItems(list)

//        for (_memo in list) {
//            Log.d(
//                "memo-db", "idx: ${_memo.idx}, 이름: ${_memo.title}, 날짜: ${_memo.datetime}, " +
//                        "카테고리: ${_memo.category}, 위치: ${_memo.location}, 별점: ${_memo.rate}, " +
//                        "한줄평: ${_memo.review}, 메모: ${_memo.content}, " +
//                        "즐겨찾기: ${_memo.isBookmark}, 작성자: ${_memo.writer}"
//            )
//        }
    }

    private fun getWriterIdx(): Int {
        val spf : SharedPreferences = getSharedPreferences("memo", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("token", 0)
    }

//    private fun removeMemo(idx: Int, position: Int){
//        val db : MemoDB = Room.databaseBuilder(this, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
//        db.memoDao().removeMemo(idx)
//
//        rvAdapter.removeItem(position)
//    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v) {
        }
    }

    private fun startSettingActivity() { //setting screen으로 넘어가는 fun
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun startViewActivity() { //view screen으로 넘어가는 fun
        val intent = Intent(this, ViewActivity::class.java)
        startActivity(intent)
    }

    private fun startPostActivity(memo: Memo) {
        val intent = Intent(this, PostActivity::class.java)
        val gson = Gson()
        val json = gson.toJson(memo)

        intent.putExtra("memo", json)
        startActivity(intent)
    }

    private fun startPostActivity() {
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }
}