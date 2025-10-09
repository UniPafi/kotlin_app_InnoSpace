package com.example.innospace.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.core.networking.SessionManager
import com.example.innospace.features.auth.domain.models.User
import com.example.innospace.features.auth.domain.repositories.AuthRepository
import com.example.innospace.features.student.domain.repositories.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val studentRepository: StudentRepository,
    private val sessionManager: SessionManager // Inyectamos el SessionManager
) : ViewModel() {

    // ... (resto de las variables no cambian) ...

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun login() {
        if (_email.value.isEmpty() || _password.value.isEmpty()) {
            _errorMessage.value = "Por favor completa todos los campos"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val loggedInUser = authRepository.login(_email.value, _password.value)

                if (loggedInUser != null && loggedInUser.token != null) {
                    sessionManager.saveAuthToken(loggedInUser.token)
                    val studentProfile = studentRepository.getStudentProfileById(loggedInUser.id)

                    if (studentProfile != null) {
                        _user.value = loggedInUser.copy(name = studentProfile.name)
                        _loginSuccess.value = true
                    } else {
                        _user.value = loggedInUser.copy(name = loggedInUser.email)
                        _loginSuccess.value = true
                    }
                } else {
                    _errorMessage.value = "Login falló. Por favor verifica tus credenciales."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetLoginState() {
        _loginSuccess.value = false
        _errorMessage.value = null
    }
}