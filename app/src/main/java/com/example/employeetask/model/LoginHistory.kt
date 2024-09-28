package com.example.employeetask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_history_table")
data class LoginHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val employeeId: Int,
    val date: String,
    val time: String,
    val address: String
)