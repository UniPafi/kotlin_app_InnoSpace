package com.example.innospace.features.applications.data.remote.dto

data class OpportunityCardDto(
    val id: Long,
    val opportunityId: Long,
    val opportunityTitle: String,
    val opportunityDescription: String?,
    val studentId: Long,
    val studentName: String?,
    val studentPhotoUrl: String?,
    val managerResponse: String?,
    val status: String?
)
