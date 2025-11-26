package com.example.innospace.features.myprojects.presentation.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.innospace.features.myprojects.domain.models.CollaborationCard

@Composable
fun CollaboratorCard(
    card: CollaborationCard,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    val statusColor = when (card.status.uppercase()) {
        "ACCEPTED" -> Color(0xFF4CAF50)
        "REJECTED" -> MaterialTheme.colorScheme.error
        "PENDING" -> Color(0xFFFF9800)
        else -> Color.Gray
    }

    val statusText = when (card.status.uppercase()) {
        "ACCEPTED" -> "Aceptado"
        "REJECTED" -> "Rechazado"
        "PENDING" -> "Pendiente"
        else -> card.status
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp, horizontal = 2.dp).animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // Barra de estado lateral
            Box(modifier = Modifier.width(6.dp).fillMaxHeight().background(statusColor))

            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val bitmap = remember(card.managerPhotoUrl) {
                        try {
                            val imageBytes = Base64.decode(card.managerPhotoUrl, Base64.DEFAULT)
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        } catch (e: Exception) { null }
                    }

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.LightGray, CircleShape)
                            .background(Color(0xFFF5F5F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (bitmap != null) {
                            Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        } else {
                            Text(text = card.managerName.take(1).uppercase(), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = card.managerName, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Text(text = card.companyName, style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            color = statusColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = statusText,
                                color = statusColor,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Postuló al proyecto:", style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray))
                Text(text = card.projectTitle, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))

                if (card.projectDescription.isNotBlank()) {
                    Text(text = card.projectDescription, style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray, fontStyle = FontStyle.Italic), maxLines = 2, overflow = TextOverflow.Ellipsis)
                }

                if (card.status.equals("PENDING", ignoreCase = true)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(
                            onClick = onReject,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                        ) {
                            Icon(Icons.Default.Close, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Rechazar")
                        }

                        Button(
                            onClick = onAccept,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Aceptar")
                        }
                    }
                }
            }
        }
    }
}