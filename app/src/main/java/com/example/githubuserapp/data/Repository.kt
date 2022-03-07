package com.example.githubuserapp.data

import com.example.githubuserapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun searchUser(options: Map<String, Any>): Flow<ResultState>
    fun detailUser(username: String): Flow<ResultState>
    fun listFollower(username: String, options: Map<String, Any>): Flow<ResultState>
    fun listFollowing(username: String, options: Map<String, Any>): Flow<ResultState>
}