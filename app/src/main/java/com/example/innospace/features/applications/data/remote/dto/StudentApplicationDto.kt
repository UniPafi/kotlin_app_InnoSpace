package com.example.innospace.features.applications.data.remote.dto

data class StudentApplicationDto(
    val id: Long,
    val studentId: Long,
    val opportunityId: Long,
    val status: String,
    val createdAt: String?
)
