package com.example.innospace.features.applications.data.remote.api

import com.example.innospace.features.applications.data.remote.dto.StudentApplicationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header

interface StudentApplicationsService {

    @GET("api/v1/student-applications")
    suspend fun getAllApplications(@Header("Authorization") auth: String? = null): Response<List<StudentApplicationDto>>

    @GET("api/v1/student-applications/{studentId}")
    suspend fun getApplicationsByStudent(
        @Path("studentId") studentId: Long,
        @Header("Authorization") auth: String? = null
    ): Response<List<StudentApplicationDto>>

    @GET("api/v1/student-applications/opportunities/{opportunityId}")
    suspend fun getApplicationsByOpportunity(@Path("opportunityId") opportunityId: Long): Response<List<StudentApplicationDto>>

    @POST("api/v1/student-applications")
    suspend fun createApplication(@Body dto: StudentApplicationDto): Response<StudentApplicationDto>

    @PATCH("api/v1/student-applications/{id}/accept")
    suspend fun acceptApplication(@Path("id") id: Long): Response<StudentApplicationDto>

    @PATCH("api/v1/student-applications/{id}/reject")
    suspend fun rejectApplication(@Path("id") id: Long): Response<StudentApplicationDto>
}
