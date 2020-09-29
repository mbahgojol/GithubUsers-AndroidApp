package com.blank.githubuser.di

import android.content.Context
import androidx.room.Room
import com.blank.githubuser.data.local.db.AppDb
import com.blank.githubuser.data.local.db.DbHelper
import com.blank.githubuser.data.local.db.DbHelperlmpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDb =
        Room.databaseBuilder(context, AppDb::class.java, "github-db")
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideDbHelper(dbHelperlmpl: DbHelperlmpl): DbHelper = dbHelperlmpl
}