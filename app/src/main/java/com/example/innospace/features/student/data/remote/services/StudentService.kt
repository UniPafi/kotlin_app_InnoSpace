package com.example.innospace.features.student.data.remote.services

import com.example.innospace.features.student.data.remote.models.CreateProjectResource
import com.example.innospace.features.student.data.remote.models.CreateStudentProfileResource
import com.example.innospace.features.student.data.remote.models.OpportunityDto
import com.example.innospace.features.student.data.remote.models.ProjectDto
import com.example.innospace.features.student.data.remote.models.StudentProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentService {
    @GET("api/v1/opportunities/{id}")
    suspend fun getOpportunityById(@Path("id") id: Long): Response<OpportunityDto>

    @GET("api/v1/projects/student/{studentId}")
    suspend fun getProjectsByStudentId(@Path("studentId") studentId: Long): Response<List<ProjectDto>>

    @GET("api/v1/student-profiles/{profileId}")
    suspend fun getStudentProfileById(@Path("profileId") profileId: Long): Response<StudentProfileDto>

    @POST("api/v1/student-profiles")
    suspend fun createStudentProfile(@Body createStudentProfileResource: CreateStudentProfileResource): Response<StudentProfileDto>

    @POST("api/v1/projects")
    suspend fun createProject(@Body createProjectResource: CreateProjectResource): Response<ProjectDto>

    @GET("api/v1/projects/{id}")
    suspend fun getProjectById(@Path("id") id: Long): Response<ProjectDto>
}