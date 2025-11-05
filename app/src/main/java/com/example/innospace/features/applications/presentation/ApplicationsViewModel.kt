package com.example.innospace.features.applications.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.core.networking.SessionManager
import com.example.innospace.features.applications.data.remote.dto.OpportunityCardDto
import com.example.innospace.features.applications.domain.repository.ApplicationRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationsViewModel @Inject constructor(
    private val repository: ApplicationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ApplicationsUiState())
    val uiState: StateFlow<ApplicationsUiState> = _uiState

    private var currentStudentId: Long? = null

    fun loadApplications(studentId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                currentStudentId = studentId
                val cards: List<OpportunityCardDto> = repository.getOpportunityCardsForStudent(studentId)
                if (cards.isEmpty()) {
                    Log.w("ApplicationsVM", "Lista de tarjetas vacía para studentId=$studentId")
                }
                _uiState.value = ApplicationsUiState(isLoading = false, applications = cards, error = null)
            } catch (e: Exception) {
                Log.e("ApplicationsVM", "Error cargando postulaciones: ${e.message}", e)
                _uiState.value = ApplicationsUiState(isLoading = false, applications = emptyList(), error = "No se pudieron cargar las postulaciones")
            }
        }
    }




    }
