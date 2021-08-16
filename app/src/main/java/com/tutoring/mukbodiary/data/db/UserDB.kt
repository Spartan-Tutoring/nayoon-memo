package com.tutoring.mukbodiary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tutoring.mukbodiary.data.entities.User

@Database(entities = [User::class], version = 1)
abstract class UserDB: RoomDatabase() {
    abstract fun userDao(): UserDao
}