package com.blank.githubuser.utils

import android.content.Context
import com.blank.githubuser.R

const val USERS = "users"
const val SEARCH_USERS = "search/$USERS"
const val FOLLOWING_USERS = "/following"
const val FOLLOWERS_USERS = "/followers"
const val USER = USERS.plus("/")
const val ERROR_NOCONNECTION = "No internet connection"
fun Context.noLocation() = this.resources.getString(R.string.no_location)
fun Context.noName() = this.resources.getString(R.string.no_name)
const val TOKEN =
    "token 124c7fb159b2e4f05aa7099e9f0b7d0c2f700532" //todo token saya b7ad8f222517bacfe0f78f894b15302255d8626f
const val ANONYM_PERSON_ICON =
    "https://www.clipartmax.com/png/full/129-1295793_agent-anonymous-incognito-private-secret-unknown-anonymous-person-icon.png"