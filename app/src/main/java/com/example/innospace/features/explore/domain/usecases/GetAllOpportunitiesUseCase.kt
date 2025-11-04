package com.example.innospace.features.explore.domain.usecases

import com.example.innospace.features.explore.domain.repositories.OpportunityRepository
import javax.inject.Inject

class GetAllOpportunitiesUseCase @Inject constructor(
    private val repository: OpportunityRepository
) {
    suspend operator fun invoke() = repository.getPublishedOpportunities()
}