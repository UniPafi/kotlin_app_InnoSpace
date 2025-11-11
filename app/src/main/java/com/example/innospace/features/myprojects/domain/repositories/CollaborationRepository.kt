package com.example.innospace.features.myprojects.domain.repositories

import com.example.innospace.features.myprojects.domain.models.CollaborationCard
import com.example.innospace.features.myprojects.domain.models.CollaborationRequest

interface CollaborationRepository {
    suspend fun getCollaborationCards(projectId: Long): List<CollaborationCard>
    suspend fun acceptCollaboration(collaborationId: Long): CollaborationRequest?
    suspend fun rejectCollaboration(collaborationId: Long): CollaborationRequest?
}