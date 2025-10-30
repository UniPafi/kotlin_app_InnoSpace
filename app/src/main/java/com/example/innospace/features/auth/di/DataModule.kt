package com.example.innospace.features.auth.di

import android.content.Context
import com.example.innospace.core.networking.ApiConstants
import com.example.innospace.core.networking.AuthInterceptor
import com.example.innospace.core.networking.SessionManager
import com.example.innospace.features.auth.data.remote.AuthService
import com.example.innospace.features.auth.data.repositories.AuthRepositoryImpl
import com.example.innospace.features.auth.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }




    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }




}