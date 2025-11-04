package com.example.innospace.features.explore.domain.repositories

import com.example.innospace.features.explore.domain.model.OpportunityCard
import com.example.innospace.features.explore.domain.model.OpportunityDetail


interface OpportunityRepository {
    suspend fun getPublishedOpportunities(): List<OpportunityCard>
    suspend fun applyToOpportunity(opportunityId: Long, studentId: Long)

    suspend fun getOpportunityDetail(id: Long): OpportunityDetail
}