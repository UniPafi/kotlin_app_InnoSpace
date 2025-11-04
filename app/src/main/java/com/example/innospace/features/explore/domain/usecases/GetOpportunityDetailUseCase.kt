package com.example.innospace.features.explore.domain.usecases

import com.example.innospace.features.explore.domain.model.OpportunityDetail
import com.example.innospace.features.explore.domain.repositories.OpportunityRepository
import javax.inject.Inject

class GetOpportunityDetailUseCase @Inject constructor(
    private val repository: OpportunityRepository
) {
    suspend operator fun invoke(id: Long): OpportunityDetail =
        repository.getOpportunityDetail(id)
}