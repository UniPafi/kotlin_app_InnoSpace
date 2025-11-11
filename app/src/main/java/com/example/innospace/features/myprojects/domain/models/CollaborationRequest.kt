package com.example.innospace.features.myprojects.domain.models

data class CollaborationRequest(
    val id: Long,
    val projectId: Long,
    val managerId: Long,
    val status: String
)