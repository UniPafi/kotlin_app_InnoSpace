package com.example.innospace.features.explore.di

import com.example.innospace.features.explore.data.remote.services.ManagerProfileService
import com.example.innospace.features.explore.data.remote.services.OpportunityService
import com.example.innospace.features.explore.data.repositories.OpportunityRepositoryImpl
import com.example.innospace.features.explore.domain.repositories.OpportunityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ExploreDataModule {

    @Provides
    @Singleton
    fun provideOpportunityService(retrofit: Retrofit): OpportunityService =
        retrofit.create(OpportunityService::class.java)

    @Provides
    @Singleton
    fun provideManagerProfileService(retrofit: Retrofit): ManagerProfileService =
        retrofit.create(ManagerProfileService::class.java)
}


