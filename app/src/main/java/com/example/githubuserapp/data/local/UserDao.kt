package com.example.githubuserapp.data.local

import androidx.room.*
import com.example.githubuserapp.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM User")
    fun loadAll(): MutableList<User>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): User

    @Delete
    fun delete(user: User)
}