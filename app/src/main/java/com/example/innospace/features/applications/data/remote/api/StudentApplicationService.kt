package com.example.innospace.features.applications.data.remote

import com.example.innospace.features.applications.data.remote.dto.StudentApplicationDto
import retrofit2.Response
import retrofit2.http.*

interface StudentApplicationsService {

    @GET("api/v1/student-applications")
    suspend fun getAllApplications(): Response<List<StudentApplicationDto>>

    @GET("api/v1/student-applications/student/{studentId}")
    suspend fun getApplicationsByStudent(@Path("studentId") studentId: Long): Response<List<StudentApplicationDto>>

    @GET("api/v1/student-applications/opportunities/{opportunityId}")
    suspend fun getApplicationsByOpportunity(@Path("opportunityId") opportunityId: Long): Response<List<StudentApplicationDto>>

    @POST("api/v1/student-applications")
    suspend fun createApplication(@Body dto: StudentApplicationDto): Response<StudentApplicationDto>

    @PATCH("api/v1/student-applications/{id}/accept")
    suspend fun acceptApplication(@Path("id") id: Long): Response<StudentApplicationDto>

    @PATCH("api/v1/student-applications/{id}/reject")
    suspend fun rejectApplication(@Path("id") id: Long): Response<StudentApplicationDto>
}
