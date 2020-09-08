package com.blank.githubuser.data.repository

import com.blank.githubuser.data.local.db.DbHelper
import com.blank.githubuser.data.model.SearchUsersResponse
import com.blank.githubuser.data.model.User
import com.blank.githubuser.data.remote.ApiHelper
import com.blank.githubuser.utils.ResultState
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DbHelper
) : ApiHelper, DbHelper {
    override fun getUsers(page: Int, per_page: Int): Single<List<User>> =
        apiHelper.getUsers(page, per_page)

    override fun getUserByUsername(username: String): Single<ResultState> =
        apiHelper.getUserByUsername(username)

    override fun getSearchUsers(
        q: String,
        page: Int,
        per_page: Int
    ): Observable<SearchUsersResponse> =
        apiHelper.getSearchUsers(q, page, per_page)

    override fun getUsersFollowing(
        username: String,
        page: Int,
        per_page: Int
    ): Single<List<User>> =
        apiHelper.getUsersFollowing(username, page, per_page)

    override fun getUsersFollowers(
        username: String,
        page: Int,
        per_page: Int
    ): Single<List<User>> =
        apiHelper.getUsersFollowers(username, page, per_page)

    override fun fetchUserByUsername(username: String): Single<User> =
        apiHelper.fetchUserByUsername(username)

    override fun insertDb(user: User): Observable<Boolean> =
        dbHelper.insertDb(user)

    override fun loadAll(): Observable<MutableList<User>> =
        dbHelper.loadAll()

    override fun findById(id: Int): Observable<User> =
        dbHelper.findById(id)

    override fun delete(user: User): Observable<Boolean> =
        dbHelper.delete(user)
}