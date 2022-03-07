package com.example.githubuserapp.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> flowApi(service: (suspend () -> T)): Flow<ResultState> = flow {
    emit(ResultState.Loading(true))
    try {
        emit(ResultState.Success(service.invoke()))
    } catch (e: Exception) {
        emit(ResultState.Error(e))
    } finally {
        emit(ResultState.Loading(false))
    }
}.flowOn(Dispatchers.IO)