package com.abhi.stageapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhi.stageapp.data.local.AccountsDB
import com.abhi.stageapp.data.local.dao.AccountsDao
import com.abhi.stageapp.data.model.Account
import com.abhi.stageapp.utils.Mappers.asEntity
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest : TestCase() {
    private lateinit var db: AccountsDB
    private lateinit var dao: AccountsDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AccountsDB::class.java).build()
        dao = db.accountsDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

   @Test
    fun writeAndRead(): Unit = runBlocking {
        val account = Account(id = "1",
            name = "",
            profileImage = "",
            stories = emptyList())

        dao.insert(account.asEntity())
        val accounts = dao.getAccounts()
        assertThat(accounts.isNotEmpty()).isTrue()
    }

}