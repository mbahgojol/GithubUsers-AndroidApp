package com.blank.githubuser.data.local.db

import com.blank.githubuser.data.model.User
import io.reactivex.Observable
import javax.inject.Inject

class DbHelperlmpl @Inject constructor(private val appDb: AppDb) : DbHelper {
    override fun insertDb(user: User): Observable<Boolean> =
        Observable.fromCallable {
            appDb.daoUser().insert(user)
            true
        }

    override fun loadAll(): Observable<MutableList<User>> =
        Observable.fromCallable {
            appDb.daoUser().loadAll()
        }

    override fun findById(id: Int): Observable<User> =
        appDb.daoUser().findById(id)

    override fun delete(user: User): Observable<Boolean> =
        Observable.fromCallable {
            appDb.daoUser().delete(user)
            true
        }
}