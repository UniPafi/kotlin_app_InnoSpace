package com.example.innospace.features.student.domain.repositories

import com.example.innospace.shared.models.Opportunity
import com.example.innospace.shared.models.Project
import com.example.innospace.shared.models.StudentProfile

interface StudentRepository {
    suspend fun getOpportunityById(id: Long): Opportunity?
    suspend fun getProjectsByStudentId(studentId: Long): List<Project>
    suspend fun getStudentProfileById(profileId: Long): StudentProfile?
    suspend fun createStudentProfile(userId: Long, name: String): StudentProfile?
    suspend fun createProject(studentId: Long, title: String, description: String): Project?
    suspend fun getProjectById(id: Long): Project?
}