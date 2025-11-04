package com.example.innospace.features.explore.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.explore.domain.model.OpportunityDetail
import com.example.innospace.features.explore.domain.usecases.ApplyToOpportunityUseCase
import com.example.innospace.features.explore.domain.usecases.GetOpportunityDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpportunityDetailViewModel @Inject constructor(
    private val getOpportunityDetailUseCase: GetOpportunityDetailUseCase,
    private val applyToOpportunityUseCase: ApplyToOpportunityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<OpportunityDetailUiState>(OpportunityDetailUiState.Loading)
    val uiState: StateFlow<OpportunityDetailUiState> = _uiState

    fun loadOpportunityDetail(id: Long) {
        viewModelScope.launch {
            try {
                _uiState.value = OpportunityDetailUiState.Loading
                val detail = getOpportunityDetailUseCase(id)
                _uiState.value = OpportunityDetailUiState.Success(detail)
            } catch (e: Exception) {
                _uiState.value = OpportunityDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun applyToOpportunity(opportunityId: Long, studentId: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                applyToOpportunityUseCase(opportunityId, studentId)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "No se pudo postular")
            }
        }
    }



}

sealed class OpportunityDetailUiState {
    data object Loading : OpportunityDetailUiState()
    data class Success(val detail: OpportunityDetail) : OpportunityDetailUiState()
    data class Error(val message: String) : OpportunityDetailUiState()
}
