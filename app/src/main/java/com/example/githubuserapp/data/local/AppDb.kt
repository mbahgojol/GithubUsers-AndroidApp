package com.example.githubuserapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubuserapp.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun daoUser(): UserDao
}