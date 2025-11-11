package com.example.innospace.features.applications.data.remote.dto

data class ApplicationDto(
    val id: Long,
    val title: String,
    val company: String,
    val requirements: List<String>,
    val status: String
)