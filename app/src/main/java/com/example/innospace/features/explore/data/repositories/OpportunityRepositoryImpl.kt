package com.example.innospace.features.explore.data.repositories

import com.example.innospace.features.explore.data.remote.services.ManagerProfileService
import com.example.innospace.features.explore.data.remote.services.OpportunityService
import com.example.innospace.features.explore.domain.model.Opportunity
import com.example.innospace.features.explore.domain.repositories.OpportunityRepository
import javax.inject.Inject


class OpportunityRepositoryImpl @Inject constructor(
    private val opportunityService: OpportunityService,
    private val managerProfileService: ManagerProfileService
) : OpportunityRepository {

    override suspend fun getPublishedOpportunities(): List<Opportunity> {
        val opportunities = opportunityService.getAllOpportunities()
            .filter { it.status == "PUBLISHED" }

        return opportunities.map { opp ->
            val manager = managerProfileService.getManagerProfileById(opp.companyId)
            Opportunity(
                id = opp.id,
                title = opp.title,
                description = opp.description,
                category = opp.category,
                companyName = manager.companyName ?: "Empresa desconocida"
            )
        }
    }
}