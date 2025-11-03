package com.example.innospace.features.myprojects.presentation

import com.example.innospace.features.myprojects.domain.models.Project

data class MyProjectsUiState(
    val isLoading: Boolean = false,
    val projects: List<Project> = emptyList(),
    val error: String? = null
)

data class ProjectDetailUiState(
    val isLoading: Boolean = false,
    val project: Project? = null,
    val isDeleted: Boolean = false,
    val error: String? = null
)

data class AddProjectUiState(
    val title: String = "",
    val description: String = "",
    val summary: String = "",
    val category: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)