package com.example.innospace.features.auth.di

import com.example.innospace.core.networking.ApiConstants.BASE_URL
import com.example.innospace.features.auth.data.remote.AuthService
import com.example.innospace.features.auth.data.repositories.AuthRepositoryImpl
import com.example.innospace.features.auth.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  RemoteModule {



    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}
