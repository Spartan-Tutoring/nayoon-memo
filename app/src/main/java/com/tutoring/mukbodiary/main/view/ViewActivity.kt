package com.tutoring.mukbodiary.main.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.tutoring.mukbodiary.BaseActivity
import com.tutoring.mukbodiary.data.db.MemoDB
import com.tutoring.mukbodiary.data.entities.Memo
import com.tutoring.mukbodiary.databinding.ActivityMainBinding
import com.tutoring.mukbodiary.databinding.ActivityPostBinding
import com.tutoring.mukbodiary.databinding.ActivityViewBinding
import com.tutoring.mukbodiary.main.MainActivity
import com.tutoring.mukbodiary.main.PostActivity
import com.tutoring.mukbodiary.setting.SettingActivity

class ViewActivity : BaseActivity() {
    private lateinit var binding: ActivityViewBinding
    private lateinit var categoryRvAdapter: ViewRvAdapter
    private lateinit var bookmarkRvAdapter: ViewRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //리스트<->지도
        binding.viewModeBtn.setOnClickListener {
            Toast.makeText(this, "지도모드", Toast.LENGTH_SHORT).show()
        }

        //프로필
        binding.viewProfileIv.setOnClickListener {
            startSettingActivity()
        }

        //모아보기
        binding.viewListBtn.setOnClickListener {
            startMainActivity()
        }

        //메모작성
        binding.viewPostBtn.setOnClickListener {
            startPostActivity()
        }

        initCategoryRecyclerView()
        initBookmarkRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        val token = getWriterIdx()

        if(token != 0) {
//            getCategorizedMemos(token)
            getMarkedMemos(token)
        }
    }

    private fun initCategoryRecyclerView() {
        //layout manager 연결
        val lm_category = LinearLayoutManager(this)
        binding.viewCategoryRecyclerview.layoutManager = lm_category

        //adapter 연결
        categoryRvAdapter = ViewRvAdapter(this)

        //item 클릭하면 PostActivity로 이동
        categoryRvAdapter.setItemClickListner(object: ViewRvAdapter.ItemClickListner {
            override fun onClick(view: View, position: Int, memo: Memo) {
                startPostActivity(memo)
            }
        })

        binding.viewCategoryRecyclerview.adapter = categoryRvAdapter
    }

    private fun initBookmarkRecyclerView() {
        //layout manager 연결
        val lm_bookmark = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.viewBookmarkRecyclerview.layoutManager = lm_bookmark

        //adapter 연결
        bookmarkRvAdapter = ViewRvAdapter(this)

        //item 클릭하면 PostActivity로 이동
        bookmarkRvAdapter.setItemClickListner(object: ViewRvAdapter.ItemClickListner {
            override fun onClick(view: View, position: Int, memo: Memo) {
                startPostActivity(memo)
            }
        })

        binding.viewBookmarkRecyclerview.adapter = bookmarkRvAdapter
    }

//    private fun getCategorizedMemos(writer: Int) {
//        val db: MemoDB = Room.databaseBuilder(this, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
//        val categoryList = ArrayList(db.memoDao().getMemoByCategory(writer)
//
//        categoryRvAdapter.addItems(categoryList)
//    }

    private fun getMarkedMemos(writer: Int) {
        val db: MemoDB = Room.databaseBuilder(this, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
        val bookmarkList = ArrayList(db.memoDao().getMemoByBookmark(writer))

        bookmarkRvAdapter.addItems(bookmarkList)

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

    private fun getCategory(): Int {
        val spf : SharedPreferences = getSharedPreferences("memo", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("category", 0)
    }

    private fun startSettingActivity() { //setting screen으로 넘어가는 fun
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun startMainActivity() { //view screen으로 넘어가는 fun
        val intent = Intent(this, MainActivity::class.java)
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