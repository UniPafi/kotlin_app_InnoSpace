package com.example.innospace.features.explore.domain.repositories

import com.example.innospace.features.explore.domain.model.OpportunityCard
import com.example.innospace.features.explore.domain.model.OpportunityDetail


interface OpportunityRepository {
    suspend fun getPublishedOpportunities(): List<OpportunityCard>


    suspend fun getOpportunityDetail(id: Long): OpportunityDetail
}