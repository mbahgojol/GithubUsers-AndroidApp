package com.example.githubuserapp.data.model

data class UserGithubResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
) {
    data class Item(
        val avatar_url: String,
        val id: Int,
        val login: String,
    )
}