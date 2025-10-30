package com.example.innospace.features.auth.data.remote.models

data class LoginResponseDto(
    val id: Long,
    val email: String,
    val token: String
)