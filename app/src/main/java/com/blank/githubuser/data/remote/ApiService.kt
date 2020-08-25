package com.blank.githubuser.data.remote

import com.blank.githubuser.data.model.SearchUsersResponse
import com.blank.githubuser.data.model.User
import com.blank.githubuser.data.model.UsersResponse
import com.blank.githubuser.utils.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {
    @GET(USERS)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getUsers(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Observable<UsersResponse>

    @GET(USER.plus("{username}"))
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getUserByUsername(
        @Header("Authorization") authorization: String,
        @Path("username") username: String
    ): Single<User>

    @GET(SEARCH_USERS)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getSearchUsers(
        @Header("Authorization") authorization: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Observable<SearchUsersResponse>

    @GET(USER.plus("{username}").plus(FOLLOWING_USERS))
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getUsersFollowing(
        @Header("Authorization") authorization: String,
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Observable<UsersResponse>

    @GET(USER.plus("{username}").plus(FOLLOWERS_USERS))
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getUsersFollowers(
        @Header("Authorization") authorization: String,
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Observable<UsersResponse>
}