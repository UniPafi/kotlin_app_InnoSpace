package com.example.innospace.features.student.data.repositories

import com.example.innospace.features.student.data.remote.models.CreateProjectResource
import com.example.innospace.features.student.data.remote.models.CreateStudentProfileResource
import com.example.innospace.features.student.data.remote.services.StudentService
import com.example.innospace.features.student.domain.repositories.StudentRepository
import com.example.innospace.shared.models.Opportunity
import com.example.innospace.shared.models.Project
import com.example.innospace.shared.models.StudentProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentRepositoryImpl(private val service: StudentService) : StudentRepository {

    override suspend fun getOpportunityById(id: Long): Opportunity? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getOpportunityById(id)
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        return@withContext Opportunity(
                            id = dto.id,
                            companyId = dto.companyId,
                            title = dto.title,
                            description = dto.description,
                            requirements = dto.requirements,
                            status = dto.status
                        )
                    }
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    override suspend fun getProjectsByStudentId(studentId: Long): List<Project> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getProjectsByStudentId(studentId)
                if (response.isSuccessful) {
                    return@withContext response.body()?.map { dto ->
                        Project(
                            id = dto.id,
                            studentId = dto.studentId,
                            title = dto.title,
                            description = dto.description,
                            status = dto.status
                        )
                    } ?: emptyList()
                }
                return@withContext emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext emptyList()
            }
        }

    override suspend fun getStudentProfileById(profileId: Long): StudentProfile? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getStudentProfileById(profileId)
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        return@withContext StudentProfile(
                            id = dto.id,
                            userId = dto.userId,
                            name = dto.name,
                            photoUrl = dto.photoUrl,
                            skills = dto.skills,
                            experiences = dto.experiences
                        )
                    }
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    override suspend fun createStudentProfile(userId: Long, name: String): StudentProfile? =
        withContext(Dispatchers.IO) {
            try {
                val resource = CreateStudentProfileResource(userId = userId, name = name)
                val response = service.createStudentProfile(resource)
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        return@withContext StudentProfile(
                            id = dto.id,
                            userId = dto.userId,
                            name = dto.name,
                            photoUrl = dto.photoUrl,
                            skills = dto.skills,
                            experiences = dto.experiences
                        )
                    }
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    override suspend fun createProject(studentId: Long, title: String, description: String): Project? =
        withContext(Dispatchers.IO) {
            try {
                val resource = CreateProjectResource(studentId, title, description)
                val response = service.createProject(resource)
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        return@withContext Project(
                            id = dto.id,
                            studentId = dto.studentId,
                            title = dto.title,
                            description = dto.description,
                            status = dto.status
                        )
                    }
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    override suspend fun getProjectById(id: Long): Project? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getProjectById(id)
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        return@withContext Project(
                            id = dto.id,
                            studentId = dto.studentId,
                            title = dto.title,
                            description = dto.description,
                            status = dto.status
                        )
                    }
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
}