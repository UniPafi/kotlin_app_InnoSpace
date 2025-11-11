package com.example.innospace.features.myprojects.presentation.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material.icons.automirrored.filled.ShortText
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(
    viewModel: AddProjectViewModel = hiltViewModel(),
    studentId: Long,
    onProjectCreated: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val summary by viewModel.summary.collectAsState()
    val category by viewModel.category.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    LaunchedEffect(isSuccess) {
        if (isSuccess) onProjectCreated()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Publicar Proyecto",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                "Completa los detalles de tu proyecto",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.updateTitle(it) },
                label = { Text("Título del proyecto") },
                leadingIcon = { Icon(Icons.Default.Title, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()


            )

            OutlinedTextField(
                value = summary,
                onValueChange = { viewModel.updateSummary(it) },
                label = { Text("Resumen (Summary)") },
                leadingIcon = { Icon(Icons.AutoMirrored.Filled.ShortText, contentDescription = null) },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = { viewModel.updateCategory(it) },
                label = { Text("Categoría (Ej: App Móvil)") },
                leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text("Descripción Detallada") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp, max = 300.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.createProject(studentId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(10f)
                    .height(50.dp)
            ) {
                Text("Publicar", color = Color.White)

            }
        }
    }
}