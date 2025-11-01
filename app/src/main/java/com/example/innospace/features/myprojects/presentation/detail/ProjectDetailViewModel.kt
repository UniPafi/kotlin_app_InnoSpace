package com.example.innospace.features.myprojects.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.myprojects.domain.models.Project
import com.example.innospace.features.myprojects.domain.repositories.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {

    private val _project = MutableStateFlow<Project?>(null)
    val project: StateFlow<Project?> = _project

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _actionCompleted = MutableStateFlow(false)
    val actionCompleted: StateFlow<Boolean> = _actionCompleted

    fun loadProject(projectId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _actionCompleted.value = false
            try {
                _project.value = projectRepository.getProjectById(projectId)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun publishProject() {
        _project.value?.id?.let { id ->
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val publishedProject = projectRepository.publishProject(id)

                    if (publishedProject != null) {
                        _actionCompleted.value = true
                    }
                } catch (e: Exception) { e.printStackTrace() }
                finally { _isLoading.value = false }
            }
        }
    }

    fun finalizeProject() {
        _project.value?.id?.let { id ->
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val finalizedProject = projectRepository.finalizeProject(id)
                    if (finalizedProject != null) {
                        _actionCompleted.value = true
                    }
                } catch (e: Exception) { e.printStackTrace() }
                finally { _isLoading.value = false }
            }
        }
    }

    fun deleteProject() {
        _project.value?.id?.let { id ->
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    if (projectRepository.deleteProject(id)) {
                        _actionCompleted.value = true
                    }
                } catch (e: Exception) { e.printStackTrace() }
                finally { _isLoading.value = false }
            }
        }
    }
}