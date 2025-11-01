package com.example.innospace.features.myprojects.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.myprojects.domain.repositories.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _summary = MutableStateFlow("")
    val summary: StateFlow<String> = _summary

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    fun updateTitle(value: String) { _title.value = value }
    fun updateDescription(value: String) { _description.value = value }
    fun updateSummary(value: String) { _summary.value = value }
    fun updateCategory(value: String) { _category.value = value }

    fun createProject(studentId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                projectRepository.createProject(
                    studentId = studentId,
                    title = _title.value,
                    description = _description.value,
                    summary = _summary.value,
                    category = _category.value
                )
                _isSuccess.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}