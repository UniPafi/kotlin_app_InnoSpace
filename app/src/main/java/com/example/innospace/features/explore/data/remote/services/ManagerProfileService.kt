package com.example.innospace.features.explore.data.remote.services

import com.example.innospace.features.explore.data.remote.dto.ManagerProfileDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ManagerProfileService {
    @GET("/api/v1/manager-profiles/{id}")
    suspend fun getManagerProfileById(@Path("id") id: Long): ManagerProfileDto
}