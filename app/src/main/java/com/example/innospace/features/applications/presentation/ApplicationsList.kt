package com.example.innospace.features.applications.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.innospace.core.ui.theme.BlueAccent
import com.example.innospace.core.ui.theme.LightBackground
import com.example.innospace.core.ui.theme.PurplePrimary
import com.example.innospace.core.ui.theme.SurfaceLight
import com.example.innospace.core.ui.theme.TextPrimary
import com.example.innospace.core.ui.theme.TextSecondary
import com.example.innospace.features.applications.data.remote.dto.OpportunityCardDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsList(applications: List<OpportunityCardDto>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(16.dp)
    ) {
        items(applications) { card ->
            var expanded by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(containerColor = SurfaceLight),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                onClick = { expanded = !expanded }
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = card.opportunityTitle,
                        style = MaterialTheme.typography.titleLarge.copy(color = PurplePrimary)
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = if (expanded)
                            card.opportunityDescription ?: "Sin descripción"
                        else
                            (card.opportunityDescription?.take(100) ?: "Sin descripción") + if ((card.opportunityDescription?.length ?: 0) > 100) "..." else "",
                        style = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary)
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Estado: ${card.managerResponse ?: "Pendiente"}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextSecondary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        TextButton(
                            onClick = { expanded = !expanded },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (expanded) BlueAccent else PurplePrimary
                            )
                        ) {
                            Text(
                                if (expanded) "Ver menos" else "Ver más",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (expanded) BlueAccent else PurplePrimary
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

