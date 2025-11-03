package com.example.innospace.features.myprojects.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    viewModel: ProjectDetailViewModel = hiltViewModel(),
    projectId: Long,
    navController: NavController
) {
    LaunchedEffect(projectId) {
        viewModel.loadProject(projectId)
    }

    val project by viewModel.project.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val isSuccess by viewModel.isSuccess.collectAsState()

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(project?.title ?: "Detalle del Proyecto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            if (isLoading && project == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                project?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Text(it.title, style = MaterialTheme.typography.titleLarge)

                        Text(
                            text = "Estado: ${it.status}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                            color = when (it.status) {
                                "PUBLISHED" -> MaterialTheme.colorScheme.primary
                                "DRAFT" -> MaterialTheme.colorScheme.onSurface
                                "COMPLETED" -> MaterialTheme.colorScheme.secondary
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                        )

                        Text(
                            text = "Categoría: ${it.category}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = it.summary,
                            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                        )


                        Text(it.description, style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(24.dp))

                        if (it.status == "DRAFT") {
                            Button(
                                onClick = { viewModel.publishProject() },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !isLoading
                            ) {
                                Text("Publicar Proyecto")
                            }
                        }

                        if (it.status == "PUBLISHED") {
                            Button(
                                onClick = { viewModel.finalizeProject() },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !isLoading
                            ) {
                                Text("Marcar como Finalizado")
                            }
                        }


                        OutlinedButton(
                            onClick = { viewModel.deleteProject() },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoading,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                        ) {
                            Text("Eliminar Proyecto")
                        }
                    }
                } ?: run {
                    Text("No se pudo cargar el proyecto.", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}