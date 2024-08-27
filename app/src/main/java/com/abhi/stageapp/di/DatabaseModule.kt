package com.abhi.stageapp.di

import android.content.Context
import androidx.room.Room
import com.abhi.stageapp.data.local.AccountsDB
import com.abhi.stageapp.data.local.dao.AccountsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideAccountsDao(accountsDB: AccountsDB): AccountsDao {
        return accountsDB.accountsDao()
    }

    @Provides
    @Singleton
    fun provideAccountsDatabase(@ApplicationContext appContext: Context): AccountsDB {
        return Room.databaseBuilder(
            appContext,
            AccountsDB::class.java,
            "accountsdb"
        ).build()
    }
}