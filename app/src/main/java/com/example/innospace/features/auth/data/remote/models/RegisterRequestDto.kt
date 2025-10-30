package com.example.innospace.features.auth.data.remote.models

data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val accountType: String
)