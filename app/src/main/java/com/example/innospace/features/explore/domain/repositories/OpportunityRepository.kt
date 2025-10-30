package com.example.innospace.features.explore.domain.repositories

import com.example.innospace.features.explore.domain.model.Opportunity


interface OpportunityRepository {
    suspend fun getPublishedOpportunities(): List<Opportunity>
}