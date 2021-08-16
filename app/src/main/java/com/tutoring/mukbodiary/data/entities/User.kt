package com.tutoring.mukbodiary.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable") //annotation
data class User (
    val id: String,
    val pw: String,
    val name: String,
){
    @PrimaryKey(autoGenerate = true) var idx: Int = 0
}


