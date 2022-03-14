package com.example.githubuserapp.di

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.data.Repository
import com.example.githubuserapp.data.RepositoryImpl
import com.example.githubuserapp.data.local.SettingPreferences
import com.example.githubuserapp.data.local.UserDao
import com.example.githubuserapp.data.remote.RepositoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    fun provideBaseUrl() = BuildConfig.API_URL

    @Provides
    fun provideOkHttpClient(cache: Cache, @ApplicationContext context: Context) =
        OkHttpClient.Builder()
            .cache(cache)
            .apply {
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(loggingInterceptor)
                    addInterceptor(ChuckerInterceptor(context))
                }
            }.readTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit) =
        retrofit.create<RepositoryService>()

    @Provides
    fun provideRepository(
        service: RepositoryService,
        userDao: UserDao,
        preferences: SettingPreferences
    ): Repository =
        RepositoryImpl(service, userDao, preferences)
}