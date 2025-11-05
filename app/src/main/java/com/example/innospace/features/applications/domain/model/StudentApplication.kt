package com.example.innospace.features.applications.domain.model

data class StudentApplication(
    val id: Long,
    val title: String,
    val company: String,
    val requirements: List<String>,
    val status: ApplicationStatus
)

enum class ApplicationStatus {
    PENDING, ACCEPTED, REJECTED
}