package com.tutoring.mukbodiary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tutoring.mukbodiary.data.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM UserTable")
    fun getUsers(): List<User>

    @Query("SELECT idx, id, pw, name FROM UserTable WHERE id = :id AND pw = :pw")
    fun getUser(id: String, pw: String): User?

    @Query("SELECT idx, id, pw, name FROM UserTable WHERE id = :id")
    fun getUserById(id: String): User?

    @Query("SELECT idx, id, pw, name FROM UserTable WHERE name = :name")
    fun getUserByName(name: String): User?

    @Query("SELECT idx, id, pw, name FROM UserTable WHERE idx = :idx")
    fun getUserByIdx(idx: Int): User?

    @Insert
    fun insertUser(user: User)

    @Query("UPDATE UserTable SET name = :name WHERE idx = :idx")
    fun updateUserName(idx: Int, name: String)

    @Query("DELETE FROM UserTable")
    fun deleteUsers()
}