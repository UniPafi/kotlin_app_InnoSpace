package com.example.innospace.core.networking

import android.content.Context
import androidx.room.Room
import com.example.innospace.features.explore.data.local.dao.OpportunityDao
import com.example.innospace.features.explore.data.local.database.AppDatabase
import com.example.innospace.features.explore.data.local.repositories.OpportunityLocalRepository
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
object NetworkModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "innospace_db").build()

    @Provides
    fun provideOpportunityDao(db: AppDatabase): OpportunityDao = db.opportunityDao()

    @Provides
    fun provideOpportunityLocalRepository(dao: OpportunityDao): OpportunityLocalRepository =
        OpportunityLocalRepository(dao)

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor =
        AuthInterceptor(sessionManager)

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}