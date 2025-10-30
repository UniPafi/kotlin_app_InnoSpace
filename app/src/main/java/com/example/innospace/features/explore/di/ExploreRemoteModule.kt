package com.example.innospace.features.explore.di

import com.example.innospace.features.explore.data.repositories.OpportunityRepositoryImpl
import com.example.innospace.features.explore.domain.repositories.OpportunityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ExploreRemoteModule {
    @Binds
    @Singleton
    abstract fun bindOpportunityRepository(
        impl: OpportunityRepositoryImpl
    ): OpportunityRepository}