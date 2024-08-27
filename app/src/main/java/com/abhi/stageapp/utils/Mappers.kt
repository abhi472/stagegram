package com.abhi.stageapp.utils

import com.abhi.stageapp.data.local.entitites.AccountEntity
import com.abhi.stageapp.data.model.Account
import com.google.firebase.firestore.QuerySnapshot


object Mappers {
    fun Account.asEntity() = AccountEntity(
        id = id,
        name = name,
        profileImage = profileImage,
        stories = stories,
    )

    fun AccountEntity.asData() = Account(
        id = id,
        name = name,
        profileImage = profileImage,
        stories = stories,
    )

    fun getAccountListFromEntity(entities: List<AccountEntity>): List<Account> {

        val list: ArrayList<Account> = ArrayList()

        for (entity in entities) {
            list.add(entity.asData())
        }
        return list

    }

     fun getListDataFromSnapshot(snapshot: QuerySnapshot): List<Account> {
        val list: ArrayList<Account> = ArrayList()

        for (document in snapshot) {
            val account = Account(
                id = document.id,
                name = document.data["name"].toString(),
                profileImage = document.data["profileImage"].toString(),
                stories = document.data["stories"] as List<String>
            )
            list.add(account)

        }
        return list
    }


}