package com.example.innospace.features.auth.data.models

data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val accountType: String
)