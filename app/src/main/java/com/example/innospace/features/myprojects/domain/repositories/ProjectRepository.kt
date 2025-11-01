package com.example.innospace.features.myprojects.domain.repositories

import com.example.innospace.features.myprojects.domain.models.Project

interface ProjectRepository {

    suspend fun getProjectsByStudentId(studentId: Long): List<Project>

    suspend fun getProjectById(id: Long): Project?

    suspend fun createProject(
        studentId: Long,
        title: String,
        description: String,
        summary: String,
        category: String
    ): Project?

    suspend fun updateProject(
        id: Long,
        title: String,
        description: String,
        summary: String,
        category: String
    ): Project?

    suspend fun deleteProject(id: Long): Boolean

    suspend fun publishProject(id: Long): Project?

    suspend fun finalizeProject(id: Long): Project?

    suspend fun getAllProjects(): List<Project>
}