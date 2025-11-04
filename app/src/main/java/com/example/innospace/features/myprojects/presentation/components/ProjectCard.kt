package com.example.innospace.features.myprojects.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.innospace.core.ui.theme.InnoSpaceTheme
import com.example.innospace.features.myprojects.domain.models.Project

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectCard(project: Project, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = project.summary,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3
            )

            Text(
                text = "Categoría: ${project.category}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Estado: ${project.status}",
                style = MaterialTheme.typography.labelSmall,
                color = when (project.status) {
                    "PUBLISHED" -> MaterialTheme.colorScheme.primary
                    "DRAFT" -> MaterialTheme.colorScheme.onSurface
                    "COMPLETED" -> MaterialTheme.colorScheme.secondary
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ProjectCardPreview() {
    InnoSpaceTheme {
        ProjectCard(
            project = Project(
                id = 1,
                studentId = 1,
                title = "Mi Primer Proyecto",
                description = "Descripción larga...",
                summary = "Este es un resumen corto del proyecto.",
                category = "Mobile App",
                status = "PUBLISHED"
            ),
            onClick = {}
        )
    }
}