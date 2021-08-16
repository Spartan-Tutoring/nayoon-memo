package com.tutoring.mukbodiary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tutoring.mukbodiary.data.entities.Memo

@Database(entities = [Memo::class], version = 1)
abstract class MemoDB : RoomDatabase() {
    abstract fun memoDao(): MemoDao
}