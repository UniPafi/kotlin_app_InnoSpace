package com.example.innospace.features.myprojects.domain.models

data class Project(
    val id: Long,
    val studentId: Long,
    val title: String,
    val description: String,
    val summary: String,
    val category: String,
    val status: String
)