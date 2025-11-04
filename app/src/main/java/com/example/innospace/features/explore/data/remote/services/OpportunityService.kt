package com.example.innospace.features.explore.data.remote.services

import com.example.innospace.features.explore.data.remote.dto.OpportunityDto
import com.example.innospace.features.explore.data.remote.dto.StudentApplicationRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OpportunityService {
    @GET("/api/v1/opportunities")
    suspend fun getAllOpportunities(): List<OpportunityDto>

    @GET("/api/v1/opportunities/{id}")
    suspend fun getOpportunityById(@Path("id") id: Long): OpportunityDto

    @POST("/api/v1/student-applications")
    suspend fun applyToOpportunity(
        @Body request: StudentApplicationRequestDto
    )


}