package com.example.innospace.features.applications.data.repository

import android.util.Log
import com.example.innospace.features.applications.data.remote.api.StudentApplicationsService
import com.example.innospace.features.applications.data.remote.dto.OpportunityCardDto
import com.example.innospace.features.applications.data.remote.dto.StudentApplicationDto
import com.example.innospace.features.applications.domain.repository.ApplicationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val service: StudentApplicationsService
) : ApplicationRepository {

    override suspend fun getOpportunityCardsForStudent(
        studentId: Long,
        authHeader: String?
    ): List<OpportunityCardDto> = withContext(Dispatchers.IO) {
        try {
            val response = service.getOpportunityCardsByStudent(studentId, authHeader)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                val code = response.code()
                val error = response.errorBody()?.string()
                Log.e("ApplicationsRepo", "Error: code=$code, body=$error")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("ApplicationsRepo", "Network error: ${e.message}", e)
            emptyList()
        }
    }
}




