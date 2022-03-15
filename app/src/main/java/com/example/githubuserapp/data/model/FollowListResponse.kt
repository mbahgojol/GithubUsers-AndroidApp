package com.example.githubuserapp.data.model

class FollowListResponse : ArrayList<FollowListResponse.FollowListResponseItem>() {
    data class FollowListResponseItem(
        val avatar_url: String,
        val followers_url: String,
        val following_url: String,
        val id: Int,
        val login: String,
    )
}