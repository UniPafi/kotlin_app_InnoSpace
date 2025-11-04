package com.example.innospace.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.innospace.features.auth.domain.repositories.AuthRepository
import com.example.innospace.features.auth.presentation.AuthUiState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            try {
                val user = authRepository.login(email, password)
                if (user != null) {
                    _uiState.value = AuthUiState(user = user)
                } else {
                    _uiState.value = AuthUiState(error = "Invalid credentials")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState(error = e.message ?: "Unexpected error")
            }
        }
    }
}
