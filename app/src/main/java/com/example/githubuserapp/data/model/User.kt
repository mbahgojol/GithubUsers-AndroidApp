package com.example.githubuserapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String? = null,
    val bio: String? = null,
    val blog: String? = null,
    @ColumnInfo(name = "company")
    val company: String? = null,
    val created_at: String? = null,
    val email: String? = null,
    val events_url: String? = null,
    @ColumnInfo(name = "followers")
    val followers: Int? = null,
    val followers_url: String? = null,
    @ColumnInfo(name = "following")
    val following: Int? = null,
    val following_url: String? = null,
    val gists_url: String? = null,
    val gravatar_id: String? = null,
    val hireable: String? = null,
    val html_url: String? = null,
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    @ColumnInfo(name = "location")
    val location: String? = null,
    @ColumnInfo(name = "login")
    val login: String? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    val node_id: String? = null,
    val organizations_url: String? = null,
    val public_gists: Int? = null,
    @ColumnInfo(name = "public_repos")
    val public_repos: Int? = null,
    val received_events_url: String? = null,
    val repos_url: String? = null,
    val site_admin: Boolean? = null,
    val starred_url: String? = null,
    val subscriptions_url: String? = null,
    val twitter_username: String? = null,
    val type: String? = null,
    val updated_at: String? = null,
    val url: String? = null
) : Parcelable