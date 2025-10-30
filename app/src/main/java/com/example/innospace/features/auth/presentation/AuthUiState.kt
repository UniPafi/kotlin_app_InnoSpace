package com.example.innospace.features.auth.presentation

import com.example.innospace.features.auth.domain.models.User

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)