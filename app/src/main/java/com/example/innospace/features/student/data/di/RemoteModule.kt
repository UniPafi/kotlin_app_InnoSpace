package com.example.innospace.features.student.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.innospace.features.student.data.remote.services.StudentService
import com.example.innospace.features.student.data.repositories.StudentRepositoryImpl
import com.example.innospace.features.student.domain.repositories.StudentRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideStudentRepository(service: StudentService): StudentRepository {
        return StudentRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideStudentService(retrofit: Retrofit): StudentService {
        return retrofit.create(StudentService::class.java)
    }


}