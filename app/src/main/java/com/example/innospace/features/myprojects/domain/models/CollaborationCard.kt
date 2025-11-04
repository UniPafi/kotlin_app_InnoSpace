package com.example.innospace.features.myprojects.domain.models

data class CollaborationCard(
    val collaborationId: Long,
    val projectId: Long,
    val projectTitle: String,
    val projectDescription: String,
    val managerId: Long,
    val managerName: String,
    val companyName: String,
    val managerPhotoUrl: String,
    val status: String
)