package com.example.innospace.features.explore.data.remote.dto

data class ManagerProfileDto(
    val id: Long,
    val userId: Long,
    val name: String?,
    val companyName: String?,
    val description: String?,
    val location: String?,
    val photoUrl: String?
)