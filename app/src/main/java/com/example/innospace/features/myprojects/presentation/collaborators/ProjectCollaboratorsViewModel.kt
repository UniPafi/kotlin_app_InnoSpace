package com.example.innospace.features.myprojects.presentation.collaborators

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.myprojects.domain.models.CollaborationCard
import com.example.innospace.features.myprojects.domain.repositories.CollaborationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CollaboratorsUiState(
    val isLoading: Boolean = false,
    val collaborators: List<CollaborationCard> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class ProjectCollaboratorsViewModel @Inject constructor(
    private val repository: CollaborationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollaboratorsUiState())
    val uiState: StateFlow<CollaboratorsUiState> = _uiState.asStateFlow()

    private var currentProjectId: Long = 0L

    fun loadCollaborators(projectId: Long) {
        currentProjectId = projectId
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val collaborators = repository.getCollaborationCards(projectId)
                _uiState.update {
                    it.copy(isLoading = false, collaborators = collaborators, error = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudieron cargar los colaboradores")
                }
            }
        }
    }

    fun acceptCollaboration(collaborationId: Long) {
        viewModelScope.launch {
            try {
                repository.acceptCollaboration(collaborationId)
                // Recargar la lista para reflejar el cambio de estado
                loadCollaborators(currentProjectId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al aceptar la colaboración") }
            }
        }
    }

    fun rejectCollaboration(collaborationId: Long) {
        viewModelScope.launch {
            try {
                repository.rejectCollaboration(collaborationId)
                // Recargar la lista para reflejar el cambio de estado
                loadCollaborators(currentProjectId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al rechazar la colaboración") }
            }
        }
    }
}