package com.example.innospace.features.student.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.innospace.features.student.domain.repositories.StudentRepository
import com.example.innospace.shared.models.Opportunity
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _opportunities = MutableStateFlow<List<Opportunity>>(emptyList())
    val opportunities: StateFlow<List<Opportunity>> = _opportunities

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun loadOpportunities() {
        _isLoading.value = true

        viewModelScope.launch {
            try {

                val mockOpportunities = listOf(
                    Opportunity(
                        id = 1,
                        companyId = 1,
                        title = "Desarrollador Android Junior",
                        description = "Buscamos desarrollador Android con conocimientos en Kotlin y Jetpack Compose para unirse a nuestro equipo.",
                        requirements = listOf("Kotlin", "Jetpack Compose", "Retrofit"),
                        status = "PUBLISHED"
                    ),
                    Opportunity(
                        id = 2,
                        companyId = 1,
                        title = "Diseñador UX/UI",
                        description = "Buscamos diseñador creativo para trabajar en aplicaciones móviles innovadoras.",
                        requirements = listOf("Figma", "Design Thinking", "Prototyping"),
                        status = "PUBLISHED"
                    )
                )
                _opportunities.value = mockOpportunities
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}