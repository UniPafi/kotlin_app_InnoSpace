package com.example.innospace.features.explore.data.remote.services

import com.example.innospace.features.explore.data.remote.dto.OpportunityDto
import retrofit2.http.GET

interface OpportunityService {
    @GET("/api/v1/opportunities")
    suspend fun getAllOpportunities(): List<OpportunityDto>
}