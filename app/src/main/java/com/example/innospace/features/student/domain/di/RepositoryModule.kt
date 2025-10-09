package com.example.innospace.features.student.domain.di

import com.example.innospace.features.student.data.repositories.StudentRepositoryImpl
import com.example.innospace.features.student.data.remote.services.StudentService
import com.example.innospace.features.student.domain.repositories.StudentRepository

object RepositoryModule {

    fun getStudentRepository(service: StudentService): StudentRepository {
        return StudentRepositoryImpl(service)
    }
}