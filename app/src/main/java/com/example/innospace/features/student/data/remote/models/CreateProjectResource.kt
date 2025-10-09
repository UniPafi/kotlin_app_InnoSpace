package com.example.innospace.features.student.data.remote.models

data class CreateProjectResource(
    val studentId: Long,
    val title: String,
    val description: String
)