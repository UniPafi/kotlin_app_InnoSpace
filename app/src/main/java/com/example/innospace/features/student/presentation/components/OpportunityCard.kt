package com.example.innospace.features.student.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.innospace.core.ui.theme.InnoSpaceTheme
import com.example.innospace.shared.models.Opportunity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpportunityCard(opportunity: Opportunity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = opportunity.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = opportunity.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )

            if (opportunity.requirements.isNotEmpty()) {
                Text(
                    text = "Requisitos: ${opportunity.requirements.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "Estado: ${opportunity.status}",
                style = MaterialTheme.typography.labelSmall,
                color = when (opportunity.status) {
                    "PUBLISHED" -> MaterialTheme.colorScheme.primary
                    "DRAFT" -> MaterialTheme.colorScheme.onSurfaceVariant
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun OpportunityCardPreview() {
    InnoSpaceTheme {
        OpportunityCard(
            opportunity = Opportunity(
                id = 1,
                companyId = 1,
                title = "Desarrollador Android Junior",
                description = "Buscamos desarrollador Android con conocimientos en Kotlin y Jetpack Compose para unirse a nuestro equipo.",
                requirements = listOf("Kotlin", "Jetpack Compose", "Retrofit"),
                status = "PUBLISHED"
            ),
            onClick = {}
        )
    }
}