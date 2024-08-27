package com.abhi.stageapp.data.local.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="profileImage")
    val profileImage: String,
    @ColumnInfo(name="stories")

    val stories: List<String>
): Serializable


class Converters {

    @TypeConverter
    fun mapListToString(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}

