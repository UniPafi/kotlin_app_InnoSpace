package com.example.innospace.shared.models

data class Opportunity(
    val id: Long,
    val companyId: Long,
    val title: String,
    val description: String,
    val requirements: List<String>,
    val status: String
)