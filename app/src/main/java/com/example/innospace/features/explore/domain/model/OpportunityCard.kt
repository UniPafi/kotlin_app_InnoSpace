package com.example.innospace.features.explore.domain.model


data class OpportunityCard(
    val id: Long,
    val title: String,
    val summary: String,
    val category: String,
    val companyName: String
)