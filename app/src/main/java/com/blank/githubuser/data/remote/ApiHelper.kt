package com.blank.githubuser.data.remote

import com.blank.githubuser.data.model.SearchUsersResponse
import com.blank.githubuser.data.model.User
import com.blank.githubuser.utils.ResultState
import io.reactivex.Observable
import io.reactivex.Single

interface ApiHelper {
    fun getUsers(page: Int, per_page: Int): Single<List<User>>

    fun getUserByUsername(username: String): Single<ResultState>

    fun getSearchUsers(q: String, page: Int, per_page: Int): Observable<SearchUsersResponse>

    fun getUsersFollowing(username: String, page: Int, per_page: Int): Single<List<User>>

    fun getUsersFollowers(username: String, page: Int, per_page: Int): Single<List<User>>

    fun fetchUserByUsername(username: String): Single<User>
}