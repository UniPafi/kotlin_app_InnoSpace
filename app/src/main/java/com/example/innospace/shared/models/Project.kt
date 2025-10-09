package com.example.innospace.shared.models

data class Project(
    val id: Long,
    val studentId: Long,
    val title: String,
    val description: String,
    val status: String
)