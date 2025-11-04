package com.example.innospace.features.profile.domain.models

data class StudentProfile(
    val id: Long,
    val userId: Long,
    val name: String,
    val photoUrl: String?,
    val description: String?,
    val phoneNumber: String?,
    val portfolioUrl: String?,
    val skills: List<String>?,
    val experiences: List<String>?
)

