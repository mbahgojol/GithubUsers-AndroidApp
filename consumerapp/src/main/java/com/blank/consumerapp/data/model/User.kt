package com.blank.consumerapp.data.model

import android.database.Cursor

data class User(
    var login: String? = null,
    var id: Int? = null,
    var publicRepos: Int? = null,
    var followers: Int? = null,
    var avatarUrl: String? = null,
    var following: Int? = null,
    var name: String? = null,
    var location: String? = null
) {
    companion object {
        fun mapperCursor(cursor: Cursor): User {
            val login = cursor.getString(cursor.getColumnIndexOrThrow("login"))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val public_repos = cursor.getInt(cursor.getColumnIndexOrThrow("public_repos"))
            val followers = cursor.getInt(cursor.getColumnIndexOrThrow("followers"))
            val avatar_url = cursor.getString(cursor.getColumnIndexOrThrow("avatar_url"))
            val following = cursor.getInt(cursor.getColumnIndexOrThrow("following"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val location = cursor.getString(cursor.getColumnIndexOrThrow("location"))

            return User(
                login,
                id,
                public_repos,
                followers,
                avatar_url,
                following,
                name,
                location
            )
        }
    }
}