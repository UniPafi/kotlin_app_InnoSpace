package com.example.innospace.features.applications.domain.repository

import com.example.innospace.features.applications.data.remote.dto.OpportunityCardDto
import com.example.innospace.features.applications.data.remote.dto.StudentApplicationDto

interface ApplicationRepository {
    suspend fun getApplicationsForStudent(studentId: Long, authHeader: String? = null): List<StudentApplicationDto>
    suspend fun getOpportunityCardsForStudent(studentId: Long, authHeader: String? = null): List<OpportunityCardDto>
}