package com.blank.githubuser.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blank.githubuser.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun daoUser(): UsersDao
}