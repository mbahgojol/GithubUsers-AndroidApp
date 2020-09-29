package com.blank.consumerapp.utils

sealed class ResultState {
    class Loading(val isloading: Boolean) : ResultState()
    class Success<T>(val data: T) : ResultState()
    class Error(val e: Throwable) : ResultState()
}