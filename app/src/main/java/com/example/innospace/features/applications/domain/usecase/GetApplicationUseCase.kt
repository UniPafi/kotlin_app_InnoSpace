package com.example.innospace.features.applications.domain.usecase

import com.example.innospace.features.applications.domain.repository.ApplicationRepository
import javax.inject.Inject

class GetApplicationsUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(studentId: Long) = repository.getOpportunityCardsForStudent(studentId)
}