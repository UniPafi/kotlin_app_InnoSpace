package com.example.innospace.features.applications.domain.usecase

import com.example.innospace.features.applications.data.remote.api.StudentApplicationsService
import com.example.innospace.features.applications.data.remote.dto.StudentApplicationDto
import javax.inject.Inject

class AcceptApplicationUseCase @Inject constructor(
    private val service: StudentApplicationsService
) {
    suspend operator fun invoke(id: Long): StudentApplicationDto? {
        val response = service.acceptApplication(id)
        return if (response.isSuccessful) response.body() else null
    }
}