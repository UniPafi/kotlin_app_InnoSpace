package com.example.innospace.features.applications.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.collections.get

@Composable
fun ApplicationsScreen(
    viewModel: ApplicationsViewModel = hiltViewModel(),
    studentId: Long
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(studentId) {
        viewModel.loadApplications(studentId)
    }

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}")
            }
        }
        state.applications.isEmpty() -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay postulaciones disponibles.")
            }
        }
        else -> {
            LazyColumn(Modifier.padding(16.dp)) {
                items(state.applications.size) { i ->
                    val card = state.applications[i]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(text = card.opportunityTitle ?: "Oportunidad ${card.opportunityId}", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text(text = "ID Postulación: ${card.id}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Alumno ID: ${card.studentId}")
                            Text(text = "Estado: ${card.status ?: "DESCONOCIDO"}")


                        }
                    }
                }
            }
        }
    }
}