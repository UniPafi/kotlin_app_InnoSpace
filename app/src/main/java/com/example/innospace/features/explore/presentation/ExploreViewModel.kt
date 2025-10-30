package com.example.innospace.features.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.explore.domain.model.Opportunity
import com.example.innospace.features.explore.domain.usecases.GetAllOpportunitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getAllOpportunitiesUseCase: GetAllOpportunitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState

    init {
        loadOpportunities()
    }

    private fun loadOpportunities() {
        viewModelScope.launch {
            try {
                _uiState.value = ExploreUiState(isLoading = true)
                val data = getAllOpportunitiesUseCase()
                _uiState.value = ExploreUiState(opportunities = data)
            } catch (e: Exception) {
                _uiState.value = ExploreUiState(error = e.message ?: "Error desconocido")
            }
        }
    }
}

data class ExploreUiState(
    val isLoading: Boolean = false,
    val opportunities: List<Opportunity> = emptyList(),
    val error: String? = null
)