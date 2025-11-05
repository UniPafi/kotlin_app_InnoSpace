package com.example.innospace.features.applications.data.remote.api

import com.example.innospace.features.applications.data.remote.dto.OpportunityCardDto
import com.example.innospace.features.applications.data.remote.dto.StudentApplicationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header

interface StudentApplicationsService {

    @GET("api/v1/opportunity-cards/students/{studentId}")
    suspend fun getOpportunityCardsByStudent(
        @Path("studentId") studentId: Long,
        @Header("Authorization") auth: String? = null
    ): Response<List<OpportunityCardDto>>

    @POST("api/v1/student-applications")
    suspend fun createApplication(@Body dto: StudentApplicationDto): Response<StudentApplicationDto>
}

