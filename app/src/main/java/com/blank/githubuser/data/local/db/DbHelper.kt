package com.blank.githubuser.data.local.db

import com.blank.githubuser.data.model.User
import io.reactivex.Observable

interface DbHelper {
    fun insertDb(user: User): Observable<Boolean>
    fun loadAll(): Observable<MutableList<User>>
    fun findById(id: Int): Observable<User>
    fun delete(user: User): Observable<Boolean>
}