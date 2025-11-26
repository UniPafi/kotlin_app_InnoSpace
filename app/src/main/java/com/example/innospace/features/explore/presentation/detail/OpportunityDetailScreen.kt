package com.example.innospace.features.explore.presentation.detail

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.innospace.features.explore.presentation.explore.ErrorView
import com.example.innospace.features.explore.presentation.explore.LoadingView
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class, ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        when (uiState) {
            is OpportunityDetailUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingView() }
            is OpportunityDetailUiState.Error -> ErrorView((uiState as OpportunityDetailUiState.Error).message)
            is OpportunityDetailUiState.Success -> {
                val detail = (uiState as OpportunityDetailUiState.Success).detail
                val context = LocalContext.current

                val bitmap = remember(detail.companyPhotoUrl) {
                    try {
                        val imageBytes = Base64.decode(detail.companyPhotoUrl, Base64.DEFAULT)
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    } catch (_: Exception) {
                        null
                    }
                }

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "Detalle",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = onBack) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Volver",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color.Transparent
                            )
                        )
                    },
                    containerColor = Color.Transparent
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .padding(top = padding.calculateTopPadding())
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Foto de la empresa",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.White.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Business,
                                        contentDescription = "Sin imagen",
                                        tint = Color.White,
                                        modifier = Modifier.size(64.dp)
                                    )
                                }
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = (-24).dp),
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = detail.title,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(
                                    text = detail.companyName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )

                                Spacer(Modifier.height(16.dp))

                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    AssistChip(
                                        onClick = {},
                                        label = { Text(detail.category) },
                                        colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                    )
                                    detail.companyLocation?.let {
                                        AssistChip(
                                            onClick = {},
                                            label = { Text(it) },
                                            colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                        )
                                    }
                                }

                                Divider(modifier = Modifier.padding(vertical = 20.dp), color = MaterialTheme.colorScheme.outlineVariant)

                                Text("Descripción", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = detail.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(Modifier.height(20.dp))

                                if (detail.requirements.isNotEmpty()) {
                                    Text("Requisitos", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                    Spacer(Modifier.height(8.dp))
                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        detail.requirements.forEach { req ->
                                            Row(verticalAlignment = Alignment.Top) {
                                                Text("• ", color = MaterialTheme.colorScheme.primary)
                                                Text(req, style = MaterialTheme.typography.bodyMedium)
                                            }
                                        }
                                    }
                                }

                                Spacer(Modifier.height(32.dp))

                                Button(
                                    onClick = {
                                        viewModel.applyToOpportunity(
                                            opportunityId = detail.id,
                                            studentId = studentId,
                                            onSuccess = { Toast.makeText(context, "Postulación exitosa", Toast.LENGTH_SHORT).show() },
                                            onError = { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth().height(52.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                    contentPadding = PaddingValues()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                brush = Brush.horizontalGradient(
                                                    listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                                                )
                                            )
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Postular a esta oportunidad", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}