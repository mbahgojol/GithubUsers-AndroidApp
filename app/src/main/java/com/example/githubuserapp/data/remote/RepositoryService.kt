package com.example.githubuserapp.data.remote

import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.data.model.FollowListResponse
import com.example.githubuserapp.data.model.UserGithubResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RepositoryService {
    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUser(
        @Header("Authorization") token: String,
        @QueryMap options: Map<String, Any>
    ): UserGithubResponse

    @GET("users/{username}")
    suspend fun detailUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): User

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun listFollower(
        @Header("Authorization") token: String,
        @Path("username") username: String,
        @QueryMap options: Map<String, Any>
    ): FollowListResponse

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun listFollowing(
        @Header("Authorization") token: String,
        @Path("username") username: String,
        @QueryMap options: Map<String, Any>
    ): FollowListResponse
}