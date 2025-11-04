package com.example.innospace.features.myprojects.presentation.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import com.example.innospace.features.myprojects.domain.models.CollaborationCard

@Composable
fun CollaboratorCard(
    card: CollaborationCard,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // --- SECCIÓN DEL MANAGER ---
            Row(verticalAlignment = Alignment.CenterVertically) {

                // Lógica de Imagen Base64
                val bitmap = remember(card.managerPhotoUrl) {
                    try {
                        val imageBytes = Base64.decode(card.managerPhotoUrl, Base64.DEFAULT)
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    } catch (e: Exception) {
                        null // Retorna null si la decodificación falla
                    }
                }

                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = card.managerName,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder si no hay imagen
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "N/A",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.DarkGray
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(card.managerName, style = MaterialTheme.typography.titleMedium)
                    Text(card.companyName, style = MaterialTheme.typography.bodyMedium)
                }
            }

            // --- SECCIÓN DEL PROYECTO (INFORMACIÓN AÑADIDA) ---
            // Nota: Esta información será la misma para todas las cards en esta pantalla,
            // pero se añade según la estructura del DTO.
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            Column {
                Text(
                    "Sobre el proyecto (info. de solicitud):",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    card.projectTitle,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    card.projectDescription,
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    maxLines = 2
                )
            }


            // --- SECCIÓN DE ESTADO Y ACCIONES ---
            Text(
                text = "Estado: ${card.status}",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = when (card.status.uppercase()) {
                    "ACCEPTED" -> MaterialTheme.colorScheme.primary
                    "REJECTED" -> MaterialTheme.colorScheme.error
                    "PENDING" -> MaterialTheme.colorScheme.secondary
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            // Mostrar botones solo si el estado es PENDING
            if (card.status.equals("PENDING", ignoreCase = true)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Aceptar")
                    }
                    Button(
                        onClick = onReject,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Denegar")
                    }
                }
            }
        }
    }
}
