package com.blank.githubuser.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.blank.githubuser.data.local.db.UsersDao
import com.blank.githubuser.di.DbModule

class FavoriteContentProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY = "com.blank.githubuser.data.provider"
        private const val TABLE_NAME = "User"
        private const val USERS = 1
        private const val USER = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            uriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                USERS
            )
        }
    }

    private lateinit var userDao: UsersDao

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun onCreate(): Boolean {
        context?.let {
            val database = DbModule.provideDb(it)
            userDao = database.daoUser()
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER -> userDao.loadAllCursor()
            else -> null
        }
    }
}
