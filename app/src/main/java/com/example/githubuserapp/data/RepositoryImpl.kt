package com.example.githubuserapp.data

import androidx.lifecycle.LiveData
import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.data.local.SettingPreferences
import com.example.githubuserapp.data.local.UserDao
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.data.remote.RepositoryService
import com.example.githubuserapp.utils.ResultState
import com.example.githubuserapp.utils.flowApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class RepositoryImpl constructor(
    private val service: RepositoryService,
    private val dao: UserDao,
    private val pref: SettingPreferences
) : Repository {

    override fun searchUser(options: Map<String, Any>): Flow<ResultState> = flow {
        emit(ResultState.Loading(true))
        try {
            val response =
                service.searchUser(BuildConfig.TOKEN, options)
            val data = mutableListOf<User>()

            withContext(Dispatchers.Default) {
                response.items.forEach {
                    val detail = service.detailUser(BuildConfig.TOKEN, it.login)
                    data.add(detail)
                }
            }
            emit(ResultState.Success(data))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        } finally {
            emit(ResultState.Loading(false))
        }
    }.flowOn(Dispatchers.IO)

    override fun detailUser(username: String): Flow<ResultState> = flowApi {
        service.detailUser(BuildConfig.TOKEN, username)
    }

    override fun listFollower(username: String, options: Map<String, Any>): Flow<ResultState> =
        flow {
            emit(ResultState.Loading(true))
            try {
                val response =
                    service.listFollower(BuildConfig.TOKEN, username, options)
                val data = mutableListOf<User>()

                withContext(Dispatchers.Default) {
                    response.forEach {
                        val detail = service.detailUser(BuildConfig.TOKEN, it.login)
                        data.add(detail)
                    }
                }
                emit(ResultState.Success(data))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultState.Error(e))
            } finally {
                emit(ResultState.Loading(false))
            }
        }.flowOn(Dispatchers.IO)

    override fun listFollowing(username: String, options: Map<String, Any>): Flow<ResultState> =
        flow {
            emit(ResultState.Loading(true))
            try {
                val response =
                    service.listFollowing(BuildConfig.TOKEN, username, options)
                val data = mutableListOf<User>()

                withContext(Dispatchers.Default) {
                    response.forEach {
                        val detail = service.detailUser(BuildConfig.TOKEN, it.login)
                        data.add(detail)
                    }
                }
                emit(ResultState.Success(data))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultState.Error(e))
            } finally {
                emit(ResultState.Loading(false))
            }
        }.flowOn(Dispatchers.IO)

    override fun addFavorite(user: User): Flow<Boolean> = flow {
        try {
            dao.insert(user)
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteFavorite(user: User): Flow<Boolean> = flow {
        try {
            dao.delete(user)
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override fun findById(id: Int): Flow<Boolean> = flow {
        try {
            val user = dao.findById(id)
            if (user != null) {
                emit(true)
            } else {
                emit(false)
            }
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(Dispatchers.IO)

    override fun listUser(): LiveData<MutableList<User>> = dao.loadAll()
    override fun getThemeSetting(): Flow<Boolean> = pref.getThemeSetting()
    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        pref.saveThemeSetting(isDarkModeActive)
}