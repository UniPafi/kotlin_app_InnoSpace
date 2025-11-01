package com.example.innospace.features.myprojects.data.repositories

import com.example.innospace.features.myprojects.data.remote.models.CreateProjectResource
import com.example.innospace.features.myprojects.data.remote.models.UpdateProjectResource
import com.example.innospace.features.myprojects.data.remote.services.ProjectService
import com.example.innospace.features.myprojects.domain.models.Project
import com.example.innospace.features.myprojects.domain.repositories.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val service: ProjectService
) : ProjectRepository {

    private fun mapDtoToDomain(dto: com.example.innospace.features.myprojects.data.remote.models.ProjectDto): Project {
        return Project(
            id = dto.id,
            studentId = dto.studentId,
            title = dto.title,
            description = dto.description ?: "",
            summary = dto.summary ?: "",
            category = dto.category ?: "",
            status = dto.status
        )
    }

    override suspend fun getProjectsByStudentId(studentId: Long): List<Project> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getProjectsByStudentId(studentId)
                if (response.isSuccessful) {
                    return@withContext response.body()?.map { mapDtoToDomain(it) } ?: emptyList()
                }
                return@withContext emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext emptyList()
            }
        }

    override suspend fun getProjectById(id: Long): Project? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getProjectById(id)
                if (response.isSuccessful) {
                    response.body()?.let { return@withContext mapDtoToDomain(it) }
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    override suspend fun createProject(
        studentId: Long,
        title: String,
        description: String,
        summary: String,
        category: String
    ): Project? = withContext(Dispatchers.IO) {
        try {
            val resource = CreateProjectResource(studentId, title, description, summary, category)
            val response = service.createProject(resource)
            if (response.isSuccessful) {
                response.body()?.let { return@withContext mapDtoToDomain(it) }
            }
            return@withContext null
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    override suspend fun updateProject(
        id: Long,
        title: String,
        description: String,
        summary: String,
        category: String
    ): Project? = withContext(Dispatchers.IO) {
        try {
            val resource = UpdateProjectResource(title, description, summary, category)
            val response = service.updateProject(id, resource)
            if (response.isSuccessful) {
                response.body()?.let { return@withContext mapDtoToDomain(it) }
            }
            return@withContext null
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    override suspend fun deleteProject(id: Long): Boolean = withContext(Dispatchers.IO) {
        try {
            service.deleteProject(id).isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun publishProject(id: Long): Project? = withContext(Dispatchers.IO) {
        try {
            val response = service.publishProject(id)
            if (response.isSuccessful) {
                response.body()?.let { return@withContext mapDtoToDomain(it) }
            }
            return@withContext null
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    override suspend fun finalizeProject(id: Long): Project? = withContext(Dispatchers.IO) {
        try {
            val response = service.finalizeProject(id)
            if (response.isSuccessful) {
                response.body()?.let { return@withContext mapDtoToDomain(it) }
            }
            return@withContext null
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    override suspend fun getAllProjects(): List<Project> = withContext(Dispatchers.IO) {
        try {
            val response = service.getAllProjects()
            if (response.isSuccessful) {
                return@withContext response.body()?.map { mapDtoToDomain(it) } ?: emptyList()
            }
            return@withContext emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList()
        }
    }
}