package com.example.innospace.features.myprojects.data.remote.services

import com.example.innospace.features.myprojects.data.remote.models.CollaborationCardDto
import com.example.innospace.features.myprojects.data.remote.models.CollaborationRequestDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface CollaborationService {
    @GET("api/v1/collaboration-cards/projects/{projectId}")
    suspend fun getCollaborationCards(@Path("projectId") projectId: Long): Response<List<CollaborationCardDto>>

    @PATCH("api/v1/collaborations/{id}/accept")
    suspend fun acceptCollaboration(@Path("id") collaborationId: Long): Response<CollaborationRequestDto>

    @PATCH("api/v1/collaborations/{id}/reject")
    suspend fun rejectCollaboration(@Path("id") collaborationId: Long): Response<CollaborationRequestDto>
}