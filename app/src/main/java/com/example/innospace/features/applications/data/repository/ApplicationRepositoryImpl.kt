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

    override suspend fun getApplicationsForStudent(
        studentId: Long,
        authHeader: String?
    ): List<StudentApplicationDto> = withContext(Dispatchers.IO) {
        try {
            val resp = try {
                service.getApplicationsByStudent(studentId, authHeader)
            } catch (e: Exception) {
                Log.e("ApplicationsRepo", "Error calling getApplicationsByStudent: ${e.message}", e)
                null
            }

            if (resp != null && resp.isSuccessful) {
                return@withContext resp.body() ?: emptyList()
            } else {
                // logear código y body (si existe)
                val code = resp?.code()
                val err = try { resp?.errorBody()?.string() } catch (_: Exception) { null }
                Log.e("ApplicationsRepo", "getApplicationsByStudent failed. code=$code errorBody=$err")
            }

            val allResp = try {
                service.getAllApplications(authHeader)
            } catch (e: Exception) {
                Log.e("ApplicationsRepo", "Error calling getAllApplications: ${e.message}", e)
                null
            }

            if (allResp != null && allResp.isSuccessful) {
                val allBody = allResp.body() ?: emptyList()
                return@withContext allBody.filter { it.studentId == studentId }
            } else {
                val code = allResp?.code()
                val err = try { allResp?.errorBody()?.string() } catch (_: Exception) { null }
                Log.e("ApplicationsRepo", "getAllApplications failed. code=$code errorBody=$err")
            }

            Log.e(
                "ApplicationsRepo",
                "Both endpoints failed. getByStudent code=${resp?.code() ?: "null"}, allResp code=${allResp?.code() ?: "null"}"
            )
            return@withContext emptyList()
        } catch (e: Exception) {
            Log.e("ApplicationsRepo", "Unexpected error: ${e.message}", e)
            return@withContext emptyList()
        }
    }

    override suspend fun getOpportunityCardsForStudent(
        studentId: Long,
        authHeader: String?
    ): List<OpportunityCardDto> = withContext(Dispatchers.IO) {
        val studentApplications = getApplicationsForStudent(studentId, authHeader)
        if (studentApplications.isEmpty()) {
            Log.w("ApplicationsRepo", "No OpportunityCardDto generated for studentId=$studentId")
            return@withContext emptyList()
        }

        return@withContext studentApplications.map { dto ->
            OpportunityCardDto(
                id = dto.id,
                opportunityId = dto.opportunityId,
                opportunityTitle = "Oportunidad ${dto.opportunityId}",
                opportunityDescription = null,
                studentId = dto.studentId,
                studentName = null,
                studentPhotoUrl = null,
                managerResponse = null,
                status = dto.status
            )
        }
    }
}