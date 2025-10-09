package com.example.innospace.features.student.presentation.myprojects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.innospace.core.navigation.Route
import com.example.innospace.core.ui.theme.InnoSpaceTheme
import com.example.innospace.features.student.domain.repositories.StudentRepository
import com.example.innospace.features.student.presentation.components.ProjectCard
import com.example.innospace.shared.models.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProjectsScreen(
    viewModel: MyProjectsViewModel = hiltViewModel(),
    navController: NavController,
    studentId: Long
) {
    val projectsState = viewModel.projects.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProjects(studentId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Proyectos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Route.AddProject.createRoute(studentId))
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Publicar Idea")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text("Cargando proyectos...")
                }
            } else {
                LazyColumn {
                    items(projectsState.value) { project ->
                        ProjectCard(
                            project = project,
                            onClick = { navController.navigate(Route.ProjectDetail.createRoute(project.id)) }
                        )
                    }
                }
            }
        }
    }
}

@HiltViewModel
class MyProjectsViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadProjects(studentId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _projects.value = repository.getProjectsByStudentId(studentId)
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyProjectsScreenPreview() {
    InnoSpaceTheme {
        MyProjectsScreen(navController = rememberNavController(), studentId = 1)
    }
}