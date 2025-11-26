package com.example.innospace.features.profile.presentation

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import java.io.ByteArrayOutputStream

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
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
        when (val state = uiState) {
            is ProfileUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is ProfileUiState.Success -> {
                ProfileContent(
                    profile = state.profile,
                    viewModel = viewModel,
                    onLogout = {
                        viewModel.logout()
                        onLogout()
                    }
                )
            }

            is ProfileUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Card(
                        modifier = Modifier.padding(24.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Button(
                                onClick = onLogout,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Volver al Login", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileContent(
    profile: com.example.innospace.features.profile.domain.models.StudentProfile,
    viewModel: ProfileViewModel,
    onLogout: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    val updateMessage by viewModel.updateMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(updateMessage) {
        updateMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearUpdateMessage()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Mi Perfil",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión", tint = Color.White)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                modifier = Modifier
                    .size(130.dp)
                    .border(4.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (!profile.photoUrl.isNullOrEmpty()) {
                        val bitmap = remember(profile.photoUrl) { base64ToBitmap(profile.photoUrl) }
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                modifier = Modifier.size(70.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(70.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Text(
                text = profile.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            if (!profile.description.isNullOrEmpty()) {
                Text(
                    text = profile.description,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.9f)),
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    SectionTitle("Información Personal", Icons.Default.Person)

                    ProfileInfoItem(label = "Teléfono", value = profile.phoneNumber, icon = Icons.Default.Phone)
                    ProfileInfoItem(label = "Portafolio", value = profile.portfolioUrl, icon = Icons.Default.Link)

                    Divider(color = MaterialTheme.colorScheme.surfaceVariant)

                    SectionTitle("Habilidades", Icons.Default.Star)

                    if (!profile.skills.isNullOrEmpty()) {
                        @OptIn(ExperimentalLayoutApi::class)
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            profile.skills.forEach { skill ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = skill,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                    } else {
                        Text("Sin habilidades registradas", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }

                    Divider(color = MaterialTheme.colorScheme.surfaceVariant)

                    SectionTitle("Experiencia", Icons.Default.Work)

                    if (!profile.experiences.isNullOrEmpty()) {
                        profile.experiences.forEach { experience ->
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(Icons.Default.ArrowRight, null, tint = MaterialTheme.colorScheme.primary)
                                Text(
                                    text = experience,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    } else {
                        Text("Sin experiencia registrada", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showEditDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
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
                            Text("Editar Perfil", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            profile = profile,
            onDismiss = { showEditDialog = false },
            onSave = { updateProfileDto ->
                viewModel.updateProfile(updateProfileDto)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun SectionTitle(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
private fun ProfileInfoItem(
    label: String,
    value: String?,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    if (!value.isNullOrEmpty()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun EditProfileDialog(
    profile: com.example.innospace.features.profile.domain.models.StudentProfile,
    onDismiss: () -> Unit,
    onSave: (com.example.innospace.features.profile.data.remote.models.UpdateProfileDto) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(profile.name) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var photoBase64 by remember { mutableStateOf<String?>(null) }
    var description by remember { mutableStateOf(profile.description ?: "") }
    var phoneNumber by remember { mutableStateOf(profile.phoneNumber ?: "") }
    var portfolioUrl by remember { mutableStateOf(profile.portfolioUrl ?: "") }

    val skills = remember { (profile.skills?.toMutableList() ?: mutableListOf()).toMutableStateList() }
    var newSkill by remember { mutableStateOf("") }
    val experiences = remember { (profile.experiences?.toMutableList() ?: mutableListOf()).toMutableStateList() }
    var newExperience by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let { photoBase64 = convertUriToBase64(context, it) }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Editar Perfil",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                .clickable { imagePickerLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedImageUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(selectedImageUri),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else if (!profile.photoUrl.isNullOrEmpty()) {
                                val bitmap = remember(profile.photoUrl) { base64ToBitmap(profile.photoUrl) }
                                if(bitmap != null) {
                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            } else {
                                Icon(Icons.Default.AddAPhoto, null, tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                    Text("Toca para cambiar", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))

                    StyledTextField(name, { name = it }, "Nombre", Icons.Default.Person)
                    StyledTextField(phoneNumber, { phoneNumber = it }, "Teléfono", Icons.Default.Phone)
                    StyledTextField(description, { description = it }, "Descripción", Icons.Default.Info, maxLines = 4)
                    StyledTextField(portfolioUrl, { portfolioUrl = it }, "Portafolio", Icons.Default.Link)

                    Divider()

                    Text("Habilidades", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StyledTextField(newSkill, { newSkill = it }, "Nueva habilidad", Icons.Default.Star, modifier = Modifier.weight(1f))
                        IconButton(onClick = { if(newSkill.isNotBlank()){ skills.add(newSkill); newSkill="" } }) {
                            Icon(Icons.Default.AddCircle, null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        skills.forEachIndexed { idx, s ->
                            InputChip(selected = false, onClick = { skills.removeAt(idx) }, label = { Text(s) }, trailingIcon = { Icon(Icons.Default.Close, null, Modifier.size(16.dp)) })
                        }
                    }

                    Text("Experiencia", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StyledTextField(newExperience, { newExperience = it }, "Nueva experiencia", Icons.Default.Work, modifier = Modifier.weight(1f))
                        IconButton(onClick = { if(newExperience.isNotBlank()){ experiences.add(newExperience); newExperience="" } }) {
                            Icon(Icons.Default.AddCircle, null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        experiences.forEachIndexed { idx, s ->
                            InputChip(selected = false, onClick = { experiences.removeAt(idx) }, label = { Text(s) }, trailingIcon = { Icon(Icons.Default.Close, null, Modifier.size(16.dp)) })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
                    ) { Text("Cancelar") }

                    Button(
                        onClick = {
                            val dto = com.example.innospace.features.profile.data.remote.models.UpdateProfileDto(
                                name = name,
                                photoUrl = photoBase64 ?: profile.photoUrl,
                                description = description.ifEmpty { null },
                                phoneNumber = phoneNumber.ifEmpty { null },
                                portfolioUrl = portfolioUrl.ifEmpty { null },
                                skills = skills.toList(),
                                experiences = experiences.toList()
                            )
                            onSave(dto)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) { Text("Guardar") }
                }
            }
        }
    }
}
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth(),
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}
private fun convertUriToBase64(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        Base64.encodeToString(byteArray, Base64.NO_WRAP)
    } catch (e: Exception) { e.printStackTrace(); null }
}

private fun base64ToBitmap(base64String: String): android.graphics.Bitmap? {
    return try {
        val cleanBase64 = base64String.replace("data:image/jpeg;base64,", "").replace("data:image/png;base64,", "").trim()
        val decodedBytes = Base64.decode(cleanBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) { e.printStackTrace(); null }
}