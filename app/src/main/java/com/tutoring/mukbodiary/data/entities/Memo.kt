package com.tutoring.mukbodiary.data.entities

import android.widget.Spinner
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MemoTable")
data class Memo(@PrimaryKey(autoGenerate = true) var idx: Int = 0,
                var title: String = "", //이름
                var datetime: String = "", //날짜
                var category: Int = 0, //카테고리
                var location: String = "", //위치
                var rate: String = "", //별점
                var review: String = "", //한줄평
                var content: String = "", //메모
                var isBookmark: String = "N", //즐겨찾기
                var writer: Int = 0) //작성자
