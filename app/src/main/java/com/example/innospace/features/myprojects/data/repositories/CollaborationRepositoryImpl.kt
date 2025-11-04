package com.example.innospace.features.myprojects.data.repositories

import com.example.innospace.features.myprojects.data.remote.models.CollaborationCardDto
import com.example.innospace.features.myprojects.data.remote.models.CollaborationRequestDto
import com.example.innospace.features.myprojects.data.remote.services.CollaborationService
import com.example.innospace.features.myprojects.domain.models.CollaborationCard
import com.example.innospace.features.myprojects.domain.models.CollaborationRequest
import com.example.innospace.features.myprojects.domain.repositories.CollaborationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CollaborationRepositoryImpl @Inject constructor(
    private val service: CollaborationService
) : CollaborationRepository {

    private fun mapDtoToDomain(dto: CollaborationCardDto): CollaborationCard {
        return CollaborationCard(
            collaborationId = dto.collaborationId,
            projectId = dto.projectId,
            projectTitle = dto.projectTitle,
            projectDescription = dto.projectDescription,
            managerId = dto.managerId,
            managerName = dto.managerName,
            companyName = dto.companyName,
            managerPhotoUrl = dto.managerPhotoUrl,
            status = dto.studentResponse
        )
    }

    private fun mapDtoToDomain(dto: CollaborationRequestDto): CollaborationRequest {
        return CollaborationRequest(
            id = dto.id,
            projectId = dto.projectId,
            managerId = dto.managerId,
            status = dto.status
        )
    }

    override suspend fun getCollaborationCards(projectId: Long): List<CollaborationCard> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getCollaborationCards(projectId)
                if (response.isSuccessful) {
                    return@withContext response.body()?.map { mapDtoToDomain(it) } ?: emptyList()
                }
                emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }

    override suspend fun acceptCollaboration(collaborationId: Long): CollaborationRequest? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.acceptCollaboration(collaborationId)
                if (response.isSuccessful) {
                    response.body()?.let { return@withContext mapDtoToDomain(it) }
                }
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    override suspend fun rejectCollaboration(collaborationId: Long): CollaborationRequest? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.rejectCollaboration(collaborationId)
                if (response.isSuccessful) {
                    response.body()?.let { return@withContext mapDtoToDomain(it) }
                }
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
}