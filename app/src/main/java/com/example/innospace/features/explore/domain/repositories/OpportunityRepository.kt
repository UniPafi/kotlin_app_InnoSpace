package com.example.innospace.features.explore.domain.repositories

import com.example.innospace.features.explore.domain.model.OpportunityCard


interface OpportunityRepository {
    suspend fun getPublishedOpportunities(): List<OpportunityCard>
}