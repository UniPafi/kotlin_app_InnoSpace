package com.example.innospace.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.core.networking.SessionManager
import com.example.innospace.features.profile.data.remote.models.UpdateProfileDto
import com.example.innospace.features.profile.domain.models.StudentProfile
import com.example.innospace.features.profile.domain.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _updateMessage = MutableStateFlow<String?>(null)
    val updateMessage: StateFlow<String?> = _updateMessage.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            val userId = sessionManager.getUserId()
            if (userId == -1L) {
                _uiState.value = ProfileUiState.Error("No hay sesión activa")
                return@launch
            }

            val profile = profileRepository.getStudentProfileByUserId(userId)
            if (profile != null) {
                _uiState.value = ProfileUiState.Success(profile)
            } else {
                _uiState.value = ProfileUiState.Error("No se pudo cargar el perfil")
            }
        }
    }

    fun updateProfile(updateProfileDto: UpdateProfileDto) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState !is ProfileUiState.Success) return@launch

            val updated = profileRepository.updateStudentProfile(
                profileId = currentState.profile.id,
                updateProfileDto = updateProfileDto
            )

            if (updated) {
                _updateMessage.value = "Perfil actualizado exitosamente"
                loadProfile()
            } else {
                _updateMessage.value = "Error al actualizar el perfil"
            }
        }
    }

    fun clearUpdateMessage() {
        _updateMessage.value = null
    }

    fun logout() {
        sessionManager.clearAuthToken()
    }
}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: StudentProfile) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

