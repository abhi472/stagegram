package com.abhi.stageapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import com.abhi.stageapp.data.model.Account
import com.abhi.stageapp.states.ApiState
import com.abhi.stageapp.usecase.MainUseCase
import com.abhi.stageapp.utils.Mappers.getAccountListFromEntity
import com.abhi.stageapp.utils.Mappers.getListDataFromSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val mainUseCase: MainUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ApiState())
    val uiState: StateFlow<ApiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            mainUseCase.getLocalAccounts()
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .catch {
                    updateUiState(
                        isError = true,
                        isLoading = false,
                        process = -1
                    )
                }
                .collect {
                    if (it.isEmpty()) {
                        db.collection("accounts").get().addOnSuccessListener { query ->
                            viewModelScope.launch {
                                mainUseCase
                                    .updateDB(query)
                                    .flowOn(Dispatchers.IO)
                            }

                            updateUiState(
                                account = getListDataFromSnapshot(query),
                                isError = false,
                                isLoading = false,
                                process = 1
                            )


                        }.addOnFailureListener {
                            updateUiState(
                                isError = true,
                                isLoading = false,
                                process = -1
                            )
                        }
                    } else {
                        updateUiState(
                            account = getAccountListFromEntity(it),
                            isError = false,
                            isLoading = false,
                            process = 1
                        )
                    }

                }
        }
    }

    private fun updateUiState(
        account: List<Account> = uiState.value.account,
        isError: Boolean = uiState.value.isError,
        isLoading: Boolean = uiState.value.isLoading,
        process: Int = uiState.value.process,
    ) {
        _uiState.value = uiState.value.copy(
            account = account,
            isError = isError,
            isLoading = isLoading,
            process = process,
        )
    }


}