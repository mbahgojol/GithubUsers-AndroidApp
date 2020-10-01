package com.blank.githubuser.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "User")
@Parcelize
data class User(
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String? = null,

    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,

    @ColumnInfo(name = "public_repos")
    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @ColumnInfo(name = "followers")
    @field:SerializedName("followers")
    val followers: Int? = null,

    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo(name = "following")
    @field:SerializedName("following")
    val following: Int? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "location")
    @field:SerializedName("location")
    val location: String? = null
) : Parcelable