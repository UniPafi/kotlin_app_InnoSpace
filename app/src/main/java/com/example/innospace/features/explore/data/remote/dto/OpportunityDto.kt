package com.example.innospace.features.explore.data.remote.dto


data class OpportunityDto(
    val id: Long,
    val companyId: Long,
    val title: String,
    val description: String,
    val summary: String,
    val category: String,
    val requirements: List<String>,
    val status: String
)