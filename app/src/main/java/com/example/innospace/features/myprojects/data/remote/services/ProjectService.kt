package com.example.innospace.features.myprojects.data.remote.services

import com.example.innospace.features.myprojects.data.remote.models.CreateProjectResource
import com.example.innospace.features.myprojects.data.remote.models.ProjectDto
import com.example.innospace.features.myprojects.data.remote.models.UpdateProjectResource
import retrofit2.Response
import retrofit2.http.*

interface ProjectService {

    @GET("api/v1/projects")
    suspend fun getAllProjects(): Response<List<ProjectDto>>

    @GET("api/v1/projects/student/{studentId}")
    suspend fun getProjectsByStudentId(@Path("studentId") studentId: Long): Response<List<ProjectDto>>

    @GET("api/v1/projects/{id}")
    suspend fun getProjectById(@Path("id") id: Long): Response<ProjectDto>

    @POST("api/v1/projects")
    suspend fun createProject(@Body createProjectResource: CreateProjectResource): Response<ProjectDto>

    @PUT("api/v1/projects/{id}")
    suspend fun updateProject(
        @Path("id") id: Long,
        @Body updateProjectResource: UpdateProjectResource
    ): Response<ProjectDto>

    @DELETE("api/v1/projects/{id}")
    suspend fun deleteProject(@Path("id") id: Long): Response<Unit>

    @POST("api/v1/projects/{id}/publish")
    suspend fun publishProject(@Path("id") id: Long): Response<ProjectDto>

    @POST("api/v1/projects/{id}/finalize")
    suspend fun finalizeProject(@Path("id") id: Long): Response<ProjectDto>
}