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
    @ColumnInfo(name = "company")
    val company: String? = null,
    @ColumnInfo(name = "followers")
    val followers: Int? = null,
    @ColumnInfo(name = "following")
    val following: Int? = null,
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    @ColumnInfo(name = "location")
    val location: String? = null,
    @ColumnInfo(name = "login")
    val login: String? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "public_repos")
    val public_repos: Int? = null,
) : Parcelable