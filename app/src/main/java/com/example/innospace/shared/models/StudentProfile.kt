package com.example.innospace.shared.models

data class StudentProfile(
    val id: Long,
    val userId: Long,
    val name: String,
    val photoUrl: String?,
    val skills: List<String>,
    val experiences: List<String>
)