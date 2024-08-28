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

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class DatabaseTest : TestCase() {
    // get reference to the LanguageDatabase and LanguageDao class
    private lateinit var db: AccountsDB
    private lateinit var dao: AccountsDao

    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, AccountsDB::class.java).build()
        dao = db.accountsDao()
    }

    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is present in the db -- if the item is present then our test cases pass
    @Test
    fun writeAndReadLanguage(): Unit = runBlocking {
        val account = Account(id = "1", name = "", profileImage = "", stories = emptyList())

        dao.insert(account.asEntity())
        val accounts = dao.getAccounts()
        assertThat(accounts.isNotEmpty()).isTrue()
    }

}