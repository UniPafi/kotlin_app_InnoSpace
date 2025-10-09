package com.example.innospace.features.auth.data.di

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

    // --- INICIO DE LA CORRECCIÓN ---
    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }
    // --- FIN DE LA CORRECCIÓN ---

    @Provides
    @Singleton
    fun provideAuthRepository(service: AuthService): AuthRepository {
        return AuthRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}