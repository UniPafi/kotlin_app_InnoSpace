package com.example.innospace.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.auth.domain.models.User
import com.example.innospace.features.auth.domain.repositories.AuthRepository
import com.example.innospace.features.student.domain.repositories.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _accountType = MutableStateFlow("")
    val accountType: StateFlow<String> = _accountType

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess: StateFlow<Boolean> = _registerSuccess

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user


    fun updateName(value: String) {
        _name.value = value
    }

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun updateAccountType(value: String) {
        _accountType.value = value
    }

    fun register() {
        if (_name.value.isEmpty() || _email.value.isEmpty() || _password.value.isEmpty() || _accountType.value.isEmpty()) {
            _errorMessage.value = "Por favor completa todos los campos"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val formattedAccountType = when (_accountType.value.trim().lowercase()) {
                    "estudiante" -> "STUDENT"
                    "gerente" -> "MANAGER"
                    else -> "UNKNOWN"
                }

                if (formattedAccountType == "UNKNOWN") {
                    _errorMessage.value = "Tipo de cuenta inválido. Usa Estudiante o Gerente."
                    _isLoading.value = false
                    return@launch
                }

                // Paso 1: Registrar el usuario
                val registeredUser = authRepository.register(_name.value, _email.value, _password.value, formattedAccountType)

                if (registeredUser != null) {
                    // Paso 2: Si el usuario es estudiante, crear su perfil inmediatamente
                    if (formattedAccountType == "STUDENT") {
                        studentRepository.createStudentProfile(userId = registeredUser.id, name = _name.value)
                    }

                    // Paso 3: Proceder con la navegación
                    _user.value = registeredUser.copy(name = _name.value)
                    _registerSuccess.value = true
                } else {
                    _errorMessage.value = "Registro falló. El email ya podría estar en uso."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}