package com.example.innospace.features.explore.presentation.detail

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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

@OptIn(ExperimentalEncodingApi::class, ExperimentalMaterial3Api::class)
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

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    val purpleGradient = remember(primaryColor, secondaryColor) {
        Brush.horizontalGradient(
            colors = listOf(primaryColor, secondaryColor)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(brush = purpleGradient)
                        ) {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = "Detalles",
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
                        }
                    },
                    containerColor = Color.White
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = padding.calculateTopPadding())
                            .verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
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
                                        .background(Color(0xFFF0F0F0)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Business,
                                        contentDescription = "Sin imagen",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(64.dp)
                                    )
                                }
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = (-32).dp),
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                            shadowElevation = 6.dp
                        ) {
                            Column {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp)
                                ) {
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
                                            color = Color(0xFF5C5C6C),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )

                                    Spacer(Modifier.height(16.dp))

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Surface(
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                            shape = RoundedCornerShape(50),
                                        ) {
                                            Text(
                                                text = detail.category,
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                            )
                                        }

                                        detail.companyLocation?.let { location ->
                                            Surface(
                                                color = Color(0xFFF5F5F5),
                                                shape = RoundedCornerShape(50),
                                            ) {
                                                Text(
                                                    text = location,
                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                        color = Color.Gray,
                                                        fontWeight = FontWeight.Medium
                                                    ),
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(Modifier.height(24.dp))

                                    Divider(color = Color(0xFFE0E0E0))

                                    Spacer(Modifier.height(24.dp))

                                    Text(
                                        text = "Descripción",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        text = detail.description,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = Color(0xFF49454F),
                                            lineHeight = 24.sp
                                        )
                                    )

                                    Spacer(Modifier.height(24.dp))

                                    if (detail.requirements.isNotEmpty()) {
                                        Text(
                                            text = "Requisitos",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                        )
                                        Spacer(Modifier.height(12.dp))
                                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            detail.requirements.forEach { req ->
                                                Row(verticalAlignment = Alignment.Top) {
                                                    Box(
                                                        modifier = Modifier
                                                            .padding(top = 8.dp)
                                                            .size(6.dp)
                                                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                                                    )
                                                    Spacer(Modifier.width(12.dp))
                                                    Text(
                                                        text = req,
                                                        style = MaterialTheme.typography.bodyMedium.copy(
                                                            color = Color(0xFF49454F)
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    Spacer(Modifier.height(40.dp))

                                    Button(
                                        onClick = {
                                            viewModel.applyToOpportunity(
                                                opportunityId = detail.id,
                                                studentId = studentId,
                                                onSuccess = { Toast.makeText(context, "Postulación exitosa", Toast.LENGTH_SHORT).show() },
                                                onError = { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(56.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent
                                        ),
                                        contentPadding = PaddingValues()
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(brush = purpleGradient),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Postular a esta oportunidad",
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                            )
                                        }
                                    }

                                    Spacer(Modifier.height(24.dp))
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .background(brush = purpleGradient)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}