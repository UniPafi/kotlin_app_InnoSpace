package com.example.innospace.features.auth.domain.models

data class User(
    val id: Long,
    val name: String? = null,
    val email: String,
    val token: String? = null,
    val accountType: String? = null
)