package com.blank.githubuser.data.model


import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
) {
    data class Item(
        @SerializedName("login")
        val login: String
    )
}