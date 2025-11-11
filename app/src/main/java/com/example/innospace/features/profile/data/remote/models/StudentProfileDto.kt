package com.example.innospace.features.profile.data.remote.models

data class StudentProfileDto(
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

