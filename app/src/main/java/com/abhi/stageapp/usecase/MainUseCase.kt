package com.abhi.stageapp.usecase

import com.abhi.stageapp.data.local.entitites.AccountEntity
import com.abhi.stageapp.data.model.Account
import com.abhi.stageapp.repository.MainRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainUseCase @Inject constructor(private val repository: MainRepository) {



    suspend fun getLocalAccounts():  Flow<List<AccountEntity>> = repository.getLocalAccounts()

     suspend  fun updateDB(snapshot: QuerySnapshot) = repository.addAccountsToDB(snapshot)





}