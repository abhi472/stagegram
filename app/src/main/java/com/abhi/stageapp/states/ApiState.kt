package com.abhi.stageapp.states

import com.abhi.stageapp.data.model.Account

data class ApiState (

    val account: List<Account> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = true,
    val process: Int = 0,
)