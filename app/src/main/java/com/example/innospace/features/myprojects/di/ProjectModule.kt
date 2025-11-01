package com.example.innospace.features.myprojects.di

import com.example.innospace.features.myprojects.data.remote.services.ProjectService
import com.example.innospace.features.myprojects.data.repositories.ProjectRepositoryImpl
import com.example.innospace.features.myprojects.domain.repositories.ProjectRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectModule {

    @Provides
    @Singleton
    fun provideProjectRepository(service: ProjectService): ProjectRepository {
        return ProjectRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideProjectService(retrofit: Retrofit): ProjectService {
        return retrofit.create(ProjectService::class.java)
    }
}