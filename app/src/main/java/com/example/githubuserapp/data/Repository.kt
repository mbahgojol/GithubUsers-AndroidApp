package com.example.githubuserapp.data

import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun searchUser(options: Map<String, Any>): Flow<ResultState>
    fun detailUser(username: String): Flow<ResultState>
    fun listFollower(username: String, options: Map<String, Any>): Flow<ResultState>
    fun listFollowing(username: String, options: Map<String, Any>): Flow<ResultState>
    fun addFavorite(user: User): Flow<Boolean>
    fun deleteFavorite(user: User): Flow<Boolean>
    fun findById(id: Int): Flow<Boolean>
    fun listUser(): LiveData<MutableList<User>>
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}