package com.example.innospace.features.student.presentation.myprojects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.student.domain.repositories.StudentRepository
import com.example.innospace.shared.models.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _project = MutableStateFlow<Project?>(null)
    val project: StateFlow<Project?> = _project

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadProject(projectId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _project.value = studentRepository.getProjectById(projectId)
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }
}