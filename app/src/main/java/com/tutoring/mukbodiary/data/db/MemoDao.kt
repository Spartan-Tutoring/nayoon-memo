package com.tutoring.mukbodiary.data.db

import android.widget.Spinner
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tutoring.mukbodiary.data.entities.Memo

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoTable")
    fun getMemos(): List<Memo>

    @Query("SELECT * FROM MemoTable WHERE writer = :writer")
    fun getMemoByWriter(writer: Int): List<Memo>

    @Insert
    fun writeMemo(memo: Memo)

    @Query("UPDATE MemoTable SET title = :title, datetime = :datetime, location = :location, review = :review, content = :content WHERE idx = :idx")
    fun editMemo(idx: Int, title: String, datetime: String,
                 location: String,  review: String, content: String)

    @Query("DELETE FROM MemoTable WHERE idx = :id")
    fun removeMemo(id: Int)

    @Query("UPDATE MemoTable SET isBookmark = :isBookmark WHERE idx = :id")
    fun bookmarkMemo(id: Int, isBookmark: String)

    @Query("SELECT * FROM MemoTable WHERE writer = :writer AND category = :category")
    fun getMemoByCategory(writer: Int, category: Int): List<Memo>

    @Query("SELECT * FROM MemoTable WHERE writer = :writer AND isBookmark=\"Y\"")
    fun getMemoByBookmark(writer: Int): List<Memo>


}