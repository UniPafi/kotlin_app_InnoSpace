package com.example.innospace.features.explore.domain.model

data class OpportunityDetail(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val requirements: List<String>,
    val companyName: String,
    val companyDescription: String
)