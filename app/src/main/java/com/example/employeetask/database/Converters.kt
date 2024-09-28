package com.example.employeetask.database

import androidx.room.TypeConverter
import com.example.employeetask.model.LoginHistory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromLoginHistoryList(value: List<LoginHistory>?): String{
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLoginHistoryList(value: String): List<LoginHistory>? {
        val listType = object : TypeToken<List<LoginHistory>>() {}.type
        return Gson().fromJson(value, listType)
    }
}