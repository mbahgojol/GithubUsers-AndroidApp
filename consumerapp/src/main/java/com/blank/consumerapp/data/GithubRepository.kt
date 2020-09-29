package com.blank.consumerapp.data

import android.content.Context
import android.net.Uri
import com.blank.consumerapp.data.model.User
import com.blank.consumerapp.utils.rx.RxCursorIterable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GithubRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val AUTHORITY = "com.blank.githubuser.data.provider"
    private val TABLE = "User"
    private val CONTENT_URI = Uri.Builder().scheme("content")
        .authority(AUTHORITY)
        .appendPath(TABLE)
        .build()

    fun loadAll(): Single<List<User>> {
        val cursor = context.contentResolver.query(
            CONTENT_URI, null, null, null, null
        )
        return cursor?.let {
            Observable.fromIterable(RxCursorIterable(it))
                .doAfterNext {
                    if (cursor.position == cursor.count - 1) cursor.close()
                }.map {
                    if (cursor.position < cursor.count)
                        User.mapperCursor(cursor)
                    else
                        User()
                }.toList()
        } as Single<List<User>>
    }
}