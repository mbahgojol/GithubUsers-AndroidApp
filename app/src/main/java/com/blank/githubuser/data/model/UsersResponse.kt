package com.blank.githubuser.data.model


import com.google.gson.annotations.SerializedName

class UsersResponse : ArrayList<UsersResponse.UsersResponseItem>() {
    data class UsersResponseItem(
        @SerializedName("login")
        val login: String
    )
}