package com.blank.githubuser.data.local.db

import androidx.room.*
import com.blank.githubuser.data.model.User
import io.reactivex.Single

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM User")
    fun loadAll(): List<User>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): Single<User>

    @Delete
    fun delete(user: User)
}