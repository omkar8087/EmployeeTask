package com.example.employeetask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_table")

data class Employee (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val username: String,
    val password: String,
    var latitude: Double,
    var longitude: Double,
    var date: String? = null,
    var time: String? = null,
    var address: String? = null,
    var history: List<LoginHistory> = emptyList()

)

//data class LoginHistory(
//    val date: String,
//    val time: String,
//    val address: String
//
//)
