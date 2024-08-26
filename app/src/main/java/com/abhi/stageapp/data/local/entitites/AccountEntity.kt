package com.abhi.stageapp.data.local.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "author")
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