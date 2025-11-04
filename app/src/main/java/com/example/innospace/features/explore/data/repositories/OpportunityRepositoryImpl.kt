package com.example.innospace.features.explore.data.repositories

import com.example.innospace.features.explore.data.remote.dto.ManagerProfileDto
import com.example.innospace.features.explore.data.remote.dto.OpportunityDto
import com.example.innospace.features.explore.data.remote.dto.StudentApplicationRequestDto
import com.example.innospace.features.explore.data.remote.services.ManagerProfileService
import com.example.innospace.features.explore.data.remote.services.OpportunityService
import com.example.innospace.features.explore.domain.model.OpportunityCard
import com.example.innospace.features.explore.domain.model.OpportunityDetail
import com.example.innospace.features.explore.domain.repositories.OpportunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class OpportunityRepositoryImpl @Inject constructor(
    private val opportunityService: OpportunityService,
    private val managerProfileService: ManagerProfileService
) : OpportunityRepository {

    override suspend fun getPublishedOpportunities(): List<OpportunityCard> {
        val opportunities = opportunityService.getAllOpportunities()
            .filter { it.status == "PUBLISHED" }

        return opportunities.map { opp ->
            val manager = managerProfileService.getManagerProfileById(opp.companyId)
            OpportunityCard(
                id = opp.id,
                title = opp.title,
                summary = opp.summary,
                category = opp.category,
                companyName = manager.companyName ?: "Empresa desconocida"
            )
        }
    }

    fun OpportunityDto.toCard(manager: ManagerProfileDto): OpportunityCard {
        return OpportunityCard(
            id = id,
            title = title,
            summary = summary,
            category = category,
            companyName = manager.companyName ?: "Empresa desconocida"
        )
    }

    fun OpportunityDto.toDetail(manager: ManagerProfileDto): OpportunityDetail {
        return OpportunityDetail(
            id = id,
            title = title,
            description = description,
            category = category,
            requirements = requirements,
            companyName = manager.companyName ?: "Empresa desconocida",
            companyDescription = manager.description ?: "",
            companyLocation = manager.location ?: "",
            companyPhotoUrl = manager.photoUrl ?: ""
        )
    }

    override suspend fun applyToOpportunity(opportunityId: Long, studentId: Long) {
        val request = StudentApplicationRequestDto(opportunityId, studentId)
        opportunityService.applyToOpportunity(request)
    }

    override suspend fun getOpportunityDetail(id: Long): OpportunityDetail = withContext(Dispatchers.IO) {
        val dto = opportunityService.getOpportunityById(id)
        val manager = managerProfileService.getManagerProfileById(dto.companyId)
        dto.toDetail(manager)
    }
}