package com.abhi.stageapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhi.stageapp.data.local.entitites.AccountEntity

@Dao
interface AccountsDao {
    @Query("SELECT * FROM accounts")
    fun getAccounts(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: AccountEntity)

    @Delete
    fun deleteAccount(account: AccountEntity)

    @Query("DELETE FROM accounts")
    fun nuke()

}