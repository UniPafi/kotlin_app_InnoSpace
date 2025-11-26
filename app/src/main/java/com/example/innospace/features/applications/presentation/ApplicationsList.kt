package com.example.innospace.features.applications.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.innospace.features.applications.data.remote.dto.OpportunityCardDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsList(applications: List<OpportunityCardDto>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(applications) { card ->
            ApplicationCardItem(card)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationCardItem(card: OpportunityCardDto) {
    var expanded by remember { mutableStateOf(false) }

    val status = card.managerResponse?.uppercase() ?: "PENDING"
    val statusIndicatorColor = when (status) {
        "ACCEPTED" -> Color(0xFF4CAF50)
        "REJECTED" -> MaterialTheme.colorScheme.error
        else -> Color(0xFFFF9800)
    }

    val statusText = when (status) {
        "ACCEPTED" -> "Aceptada"
        "REJECTED" -> "Rechazada"
        else -> "Pendiente"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        onClick = { expanded = !expanded }
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(statusIndicatorColor)
            )

            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = card.opportunityTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    Surface(
                        color = statusIndicatorColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = statusIndicatorColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = if (expanded) card.opportunityDescription ?: "Sin descripción."
                    else (card.opportunityDescription?.take(80) ?: "Sin descripción") + "...",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray),
                    maxLines = if (expanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(12.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (expanded) "Ver menos" else "Ver detalles",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}