package com.example.innospace.features.explore.presentation.detail

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlin.io.encoding.ExperimentalEncodingApi



@OptIn(ExperimentalEncodingApi::class)
@Composable
fun OpportunityDetailScreen(
    navController: NavController,
    opportunityId: Long,
    studentId: Long,
    viewModel: OpportunityDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(opportunityId) {
        viewModel.loadOpportunityDetail(opportunityId)
    }

    when (uiState) {
        is OpportunityDetailUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is OpportunityDetailUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${(uiState as OpportunityDetailUiState.Error).message}")
            }
        }
        is OpportunityDetailUiState.Success -> {
            val detail = (uiState as OpportunityDetailUiState.Success).detail
            val context = LocalContext.current
            val bitmap = remember(detail.companyPhotoUrl) {
                try {
                    val imageBytes = Base64.decode(detail.companyPhotoUrl, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                } catch (e: Exception) {
                    null
                }
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Company photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sin imagen", color = Color.DarkGray)
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text(detail.title, style = MaterialTheme.typography.headlineMedium)
                Text(detail.companyName, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                Spacer(Modifier.height(8.dp))
                Text("Categoría: ${detail.category}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                Text(detail.description)
                Spacer(Modifier.height(16.dp))
                Text("Requisitos:", style = MaterialTheme.typography.titleMedium)
                detail.requirements.forEach { req ->
                    Text("• $req")
                }
                Spacer(Modifier.height(16.dp))
                Text("Ubicación: ${detail.companyLocation}")
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        viewModel.applyToOpportunity(
                            opportunityId = detail.id,
                            studentId = studentId,
                            onSuccess = {
                                Toast.makeText(context, "Postulación enviada con éxito", Toast.LENGTH_SHORT).show()
                            },
                            onError = { message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Postular a esta oportunidad")
                }


                Spacer(Modifier.height(24.dp))
                Button(onClick = onBack, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Volver")
                }
            }
        }
    }
}
