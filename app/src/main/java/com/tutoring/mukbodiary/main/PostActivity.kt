package com.tutoring.mukbodiary.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.room.Room
import com.google.gson.Gson
import com.tutoring.mukbodiary.BaseActivity
import com.tutoring.mukbodiary.R
import com.tutoring.mukbodiary.data.db.MemoDB
import com.tutoring.mukbodiary.data.entities.Memo
import com.tutoring.mukbodiary.databinding.ActivityPostBinding
import java.text.SimpleDateFormat
import java.util.*

class PostActivity : BaseActivity() {
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dialogPostCheckIb.setOnClickListener(this)
        binding.dialogPostEditIb.setOnClickListener(this)
        binding.dialogPostDeleteIb.setOnClickListener(this)

        //메모가 이미 있을 때 세팅
        if(intent.hasExtra("memo")) {

            binding.dialogPostCheckIb.visibility = View.GONE
            binding.dialogPostEditIb.visibility = View.VISIBLE
            binding.dialogPostDeleteIb.visibility = View.VISIBLE

            val gson = Gson()
            val json = intent.getStringExtra("memo")
            val memo = gson.fromJson(json, Memo::class.java)

            setMemo(memo)
        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when(v) {
            binding.dialogPostCheckIb -> post()
            binding.dialogPostEditIb -> edit(getMemo_())
            binding.dialogPostDeleteIb -> delete(getMemo().idx)
        }
    }

    private fun post() {

//        setSpinner()

        val title = binding.postNameEt.text.toString()
        val datetime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formattedDate = formatter.format(datetime)
        val category = binding.postCategorySp.selectedItemPosition
        val location = binding.postLocationEt.text.toString()
        val rate = binding.postRatingBar
        val review = binding.postReviewEt.text.toString()
        val content = binding.postMemoEt.text.toString()

        val spf : SharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
        val editor = spf.edit()
        val token = spf.getInt("token", 0)

        if(token != 0) {
            val memo = Memo(title = title,
                datetime = formattedDate,
                category = category,
                location = location,
//                rate = rate,
                review = review,
                content= content,
                writer = token)

            val db: MemoDB = Room.databaseBuilder(this, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
            db.memoDao().writeMemo(memo)
            editor.putInt("category", category)

//            val memos = db.memoDao().getMemos()
//            for(_memo in memos) {
//                Log.d("memo-db", "idx: ${_memo.idx}, 이름: ${_memo.title}, 날짜: ${_memo.datetime}, " +
//                        "카테고리: ${_memo.category}, 위치: ${_memo.location}, 별점: ${_memo.rate}, " +
//                        "한줄평: ${_memo.review}, 메모: ${_memo.content}, " +
//                        "즐겨찾기: ${_memo.isBookmark}, 작성자: ${_memo.writer}")
//            }
            finish()
        }
    }

    private fun delete(idx: Int) {
        showDialog2("삭제하시겠습니까?")

    }

    private fun edit(memo: Memo) {
        val db: MemoDB = Room.databaseBuilder(this, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
        db.memoDao().editMemo(memo.idx, memo.title, memo.datetime, memo.location, memo.review, memo.content)

        showDialog("수정되었습니다.")
    }

    private fun setSpinner() {
        val spinner = binding.postCategorySp
        val categories = resources.getStringArray(R.array.category)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinner.adapter = adapter
    }

    override fun onCheckClicked() {
        super.onCheckClicked()
        finish()
    }

    override fun onOKClicked() {
        super.onCheckClicked()
        val db : MemoDB = Room.databaseBuilder(this, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
        db.memoDao().removeMemo(getMemo().idx)
        finish()
    }

    override fun onCancelClicked() {
        super.onCancelClicked()
        finish()
    }

    //팝업 띄웠을 때 기존 메모 세팅
    private fun setMemo(memo: Memo) {
        binding.postNameEt.setText(memo.title)
        binding.postCategorySp.setSelection(memo.category)
        binding.postLocationEt.setText(memo.location)
//        memo.rate
        binding.postReviewEt.setText(memo.review)
        binding.postMemoEt.setText(memo.content)
    }

    //edit 전 저장된 메모를 return
    private fun getMemo(): Memo {
        val gson = Gson()
        val json = intent.getStringExtra("memo")

        return gson.fromJson(json, Memo::class.java)
    }

    //edit 중인 메모를 return
    private fun getMemo_(): Memo{

//        setSpinner()

        val title = binding.postNameEt.text.toString()
        val datetime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formattedDate = formatter.format(datetime)
        val category = binding.postCategorySp.selectedItemPosition
        val location = binding.postLocationEt.text.toString()
        val rate = binding.postRatingBar
        val review = binding.postReviewEt.text.toString()
        val content = binding.postMemoEt.text.toString()

        return Memo(idx = getMemo().idx, datetime = formattedDate, title = title, location = location, review = review, content = content)
    }
}