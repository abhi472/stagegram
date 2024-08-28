package com.abhi.stageapp.repository

import android.util.Log
import com.abhi.stageapp.data.local.dao.AccountsDao
import com.abhi.stageapp.data.local.entitites.AccountEntity
import com.abhi.stageapp.data.model.Account
import com.abhi.stageapp.data.network.model.NetworkAccount
import com.abhi.stageapp.utils.Mappers.asEntity
import com.abhi.stageapp.utils.Mappers.getAccountListFromEntity
import com.abhi.stageapp.utils.Mappers.getListDataFromSnapshot
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface MainRepository {


    suspend fun getLocalAccounts(): Flow<List<AccountEntity>>
    suspend fun addAccountsToDB(querySnapshot: QuerySnapshot): Flow<Unit>

}

class MainRepositoryImpl @Inject constructor(private val accountsDao: AccountsDao): MainRepository {


    override suspend fun addAccountsToDB(querySnapshot: QuerySnapshot) : Flow<Unit> {
        return flow {
            val list = getListDataFromSnapshot(querySnapshot)
            for (item in list) {
                accountsDao.insert(item.asEntity())
            }
        }

    }
    override suspend fun getLocalAccounts(): Flow<List<AccountEntity>> =
        flow {
            emit(accountsDao.getAccounts());
        }




}