package com.example.innospace.features.student.presentation.myprojects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.student.domain.repositories.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    fun updateTitle(value: String) {
        _title.value = value
    }

    fun updateDescription(value: String) {
        _description.value = value
    }

    fun createProject(studentId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                studentRepository.createProject(studentId, _title.value, _description.value)
                _isSuccess.value = true
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}