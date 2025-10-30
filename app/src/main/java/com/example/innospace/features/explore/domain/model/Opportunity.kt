package com.example.innospace.features.explore.domain.model


data class Opportunity(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val companyName: String
)