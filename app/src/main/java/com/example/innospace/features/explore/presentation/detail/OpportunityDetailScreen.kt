package com.example.innospace.features.explore.presentation.detail

import android.graphics.BitmapFactory
import android.graphics.Paint
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


@OptIn(ExperimentalEncodingApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
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
        is OpportunityDetailUiState.Loading -> LoadingView()
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
                    TopAppBar(
                        title = { Text("Detalle de convocatoria") },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        )
                    )
                },
                containerColor = MaterialTheme.colorScheme.background
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Foto de la empresa",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = "Sin imagen",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = detail.title,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = detail.companyName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )

                        Spacer(Modifier.height(12.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AssistChip(
                                onClick = {},
                                label = { Text(detail.category) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            )

                            detail.companyLocation?.let {
                                AssistChip(
                                    onClick = {},
                                    label = { Text(it) },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                )
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        Text(
                            "Descripción",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = detail.description,
                            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 22.sp)
                        )

                        Spacer(Modifier.height(20.dp))


                        if (detail.requirements.isNotEmpty()) {
                            Text(
                                "Requisitos",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Spacer(Modifier.height(8.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                detail.requirements.forEach { req ->
                                    Row(verticalAlignment = Alignment.Top) {
                                        Text("• ", style = MaterialTheme.typography.bodyLarge)
                                        Text(req, style = MaterialTheme.typography.bodyLarge)
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
                                    onSuccess = {
                                        Toast.makeText(context, "Postulación enviada con éxito", Toast.LENGTH_SHORT).show()
                                    },
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "Postular a esta oportunidad",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = onBack,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Volver", style = MaterialTheme.typography.titleMedium)
                        }

                        Spacer(Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}