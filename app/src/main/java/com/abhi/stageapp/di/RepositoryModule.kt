package com.abhi.stageapp.di

import com.abhi.stageapp.data.local.dao.AccountsDao
import com.abhi.stageapp.repository.MainRepository
import com.abhi.stageapp.repository.MainRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAccountsDataRepository(accountsDao: AccountsDao): MainRepository {
        return MainRepositoryImpl(accountsDao)
    }

}