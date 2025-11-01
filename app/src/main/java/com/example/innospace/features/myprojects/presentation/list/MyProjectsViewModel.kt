package com.example.innospace.features.myprojects.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.myprojects.domain.repositories.ProjectRepository
import com.example.innospace.features.myprojects.presentation.MyProjectsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MyProjectsViewModel @Inject constructor(
    private val repository: ProjectRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyProjectsUiState())
    val uiState: StateFlow<MyProjectsUiState> = _uiState.asStateFlow()

    fun loadProjects(studentId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val projects = repository.getProjectsByStudentId(studentId)

                _uiState.update {
                    it.copy(isLoading = false, projects = projects, error = null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudieron cargar los proyectos")
                }
            }
        }
    }
}