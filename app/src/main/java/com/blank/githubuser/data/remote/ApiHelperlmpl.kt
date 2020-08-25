package com.blank.githubuser.data.remote

import com.blank.githubuser.data.model.SearchUsersResponse
import com.blank.githubuser.data.model.User
import com.blank.githubuser.utils.ResultState
import com.blank.githubuser.utils.TOKEN
import com.blank.githubuser.utils.rx.observableIo
import com.blank.githubuser.utils.rx.singleIo
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ApiHelperlmpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override fun getUsers(page: Int, per_page: Int): Single<List<User>> =
        apiService.getUsers(TOKEN, page, per_page)
            .observableIo()
            .flatMapIterable { it }
            .flatMap {
                apiService.getUserByUsername(TOKEN, it.login)
                    .singleIo()
                    .toObservable()
            }
            .toList()

    override fun getUserByUsername(username: String): Single<ResultState> =
        apiService.getUserByUsername(TOKEN, username)
            .singleIo()
            .map { ResultState.Success(it) }

    override fun getSearchUsers(
        q: String,
        page: Int,
        per_page: Int
    ): Observable<SearchUsersResponse> =
        apiService.getSearchUsers(TOKEN, q, page, per_page)
            .observableIo()

    override fun getUsersFollowing(
        username: String,
        page: Int,
        per_page: Int
    ): Single<List<User>> =
        apiService.getUsersFollowing(TOKEN, username, page, per_page)
            .observableIo()
            .flatMapIterable { it }
            .flatMap {
                apiService.getUserByUsername(TOKEN, it.login)
                    .singleIo()
                    .toObservable()
            }
            .toList()

    override fun getUsersFollowers(
        username: String,
        page: Int,
        per_page: Int
    ): Single<List<User>> =
        apiService.getUsersFollowers(TOKEN, username, page, per_page)
            .observableIo()
            .flatMapIterable { it }
            .flatMap {
                apiService.getUserByUsername(TOKEN, it.login)
                    .singleIo()
                    .toObservable()
            }
            .toList()


    override fun fetchUserByUsername(username: String): Single<User> =
        apiService.getUserByUsername(TOKEN, username)
            .singleIo()
}