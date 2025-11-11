package com.example.innospace.features.profile.di

import com.example.innospace.features.profile.data.remote.ProfileService
import com.example.innospace.features.profile.data.repositories.ProfileRepositoryImpl
import com.example.innospace.features.profile.domain.repositories.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileService: ProfileService): ProfileRepository {
        return ProfileRepositoryImpl(profileService)
    }
}

