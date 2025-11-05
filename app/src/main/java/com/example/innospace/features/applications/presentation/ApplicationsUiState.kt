package com.example.innospace.features.applications.presentation

import com.example.innospace.features.applications.data.remote.dto.OpportunityCardDto

data class ApplicationsUiState(
    val isLoading: Boolean = false,
    val applications: List<OpportunityCardDto> = emptyList(),
    val error: String? = null
)