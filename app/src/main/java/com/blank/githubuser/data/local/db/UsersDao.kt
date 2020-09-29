package com.blank.githubuser.data.local.db

import android.database.Cursor
import androidx.room.*
import com.blank.githubuser.data.model.User
import io.reactivex.Observable

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM User")
    fun loadAll(): MutableList<User>

    @Query("SELECT * FROM User")
    fun loadAllCursor(): Cursor

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): Observable<User>

    @Delete
    fun delete(user: User)
}