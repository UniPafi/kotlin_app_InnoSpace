package com.example.innospace.features.applications.di

import com.example.innospace.features.applications.data.remote.api.StudentApplicationsService
import com.example.innospace.features.applications.data.repository.ApplicationRepositoryImpl
import com.example.innospace.features.applications.domain.repository.ApplicationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationsModule {

    @Provides
    @Singleton
    fun provideStudentApplicationsService(retrofit: Retrofit): StudentApplicationsService =
        retrofit.create(StudentApplicationsService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationsBindingModule {
    @Binds
    @Singleton
    abstract fun bindApplicationsRepository(
        impl: ApplicationRepositoryImpl
    ): ApplicationRepository
}
