package com.abhi.stageapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhi.stageapp.data.local.dao.AccountsDao
import com.abhi.stageapp.data.local.entitites.AccountEntity
import com.abhi.stageapp.data.local.entitites.Converters

@Database(entities = [AccountEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AccountsDB: RoomDatabase() {
    abstract fun accountsDao(): AccountsDao
}