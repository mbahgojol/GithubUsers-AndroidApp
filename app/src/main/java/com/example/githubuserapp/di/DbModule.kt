package com.example.githubuserapp.di

import android.content.Context
import androidx.room.Room
import com.example.githubuserapp.data.local.AppDb
import com.example.githubuserapp.data.local.SettingPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDb =
        Room.databaseBuilder(context, AppDb::class.java, "users")
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideUserDao(appDb: AppDb) = appDb.daoUser()

    @Provides
    fun providePref(@ApplicationContext appContext: Context): SettingPreferences =
        SettingPreferences(appContext)
}