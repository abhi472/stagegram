package com.abhi.stageapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.abhi.stageapp.data.local.AccountsDB
import com.abhi.stageapp.data.local.dao.AccountsDao
import com.abhi.stageapp.data.local.entitites.AccountEntity
import com.abhi.stageapp.data.model.Account
import com.abhi.stageapp.screens.MainViewModel
import com.abhi.stageapp.states.ApiState
import com.abhi.stageapp.usecase.MainUseCase
import com.abhi.stageapp.utils.Mappers.asEntity
import com.abhi.stageapp.utils.Mappers.getAccountListFromEntity
import com.google.firebase.firestore.FirebaseFirestore
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.checkerframework.checker.units.qual.A
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException

class MyViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()


    private lateinit var mockViewModel: MainViewModel

    @Mock
    private lateinit var useCase: MainUseCase

    private lateinit var account1: Account
    private lateinit var account2:Account

    private lateinit var viewState: ApiState

    @Mock
    private lateinit var  mockFirestore: FirebaseFirestore

    private val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewState = ApiState()
        mockViewModel = MainViewModel(mockFirestore, useCase)
        account1 = Account(id = "1", name = "", profileImage = "", stories = emptyList())
        account2 = Account(id = "2", name = "", profileImage = "", stories = emptyList())
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testLoadData() = runTest {

        val accountEntityList = listOf(account1.asEntity(), account2.asEntity())

        val flow = flowOf(accountEntityList)
       // val accountList = listOf(account1, account2)

        `when`(useCase.getLocalAccounts()).thenReturn(flow)

        mockViewModel.loadData()


        advanceUntilIdle()
        val dispatchedState = mockViewModel.uiState.value
        val accountList = getAccountListFromEntity(accountEntityList)

        TestCase.assertEquals(accountList, dispatchedState.account )
    }

    @Test
    fun testUtil() {
        val accountEntity = account1.asEntity()
        val accountEntityList = listOf(account1.asEntity())
        val accountList = getAccountListFromEntity(accountEntityList)

        assertEquals(account1.id, accountEntity.id)
        assertEquals(accountList[0].id, accountEntityList[0].id)
    }


}


