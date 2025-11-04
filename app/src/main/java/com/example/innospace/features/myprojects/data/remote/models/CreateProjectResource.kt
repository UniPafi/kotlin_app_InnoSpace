package com.example.innospace.features.myprojects.data.remote.models

data class CreateProjectResource(
    val studentId: Long,
    val title: String,
    val description: String,
    val summary: String,
    val category: String
)