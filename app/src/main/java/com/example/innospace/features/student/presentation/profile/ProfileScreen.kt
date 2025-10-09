package com.example.innospace.features.student.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.core.networking.SessionManager
import com.example.innospace.core.ui.theme.InnoSpaceTheme
import com.example.innospace.features.student.presentation.components.ProfileHeader
import com.example.innospace.shared.models.StudentProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    userId: Long,
    name: String,
    email: String,
    onLogout: () -> Unit
) {
    val profileState by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val logoutComplete by viewModel.logoutComplete.collectAsState()

    LaunchedEffect(logoutComplete) {
        if (logoutComplete) {
            onLogout()
        }
    }

    LaunchedEffect(key1 = userId) {
        viewModel.loadNewUserProfile(userId, name)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Perfil") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            profileState?.let { profile ->
                ProfileHeader(
                    name = profile.name,
                    photoUrl = profile.photoUrl,
                    skills = profile.skills
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Email: $email",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.onLogoutClick() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar Sesión")
                }
            } ?: run {
                if (!isLoading) {
                    Text("No se pudo cargar el perfil")
                }
            }
        }
    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _profile = MutableStateFlow<StudentProfile?>(null)
    val profile: StateFlow<StudentProfile?> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _logoutComplete = MutableStateFlow(false)
    val logoutComplete: StateFlow<Boolean> = _logoutComplete

    fun onLogoutClick() {
        sessionManager.clearAuthToken()
        _logoutComplete.value = true
    }

    fun loadNewUserProfile(userId: Long, name: String) {
        _isLoading.value = true
        _profile.value = StudentProfile(
            id = userId,
            userId = userId,
            name = name,
            photoUrl = null,
            skills = emptyList(),
            experiences = emptyList()
        )
        _isLoading.value = false
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    InnoSpaceTheme {
        ProfileScreen(userId = 1, name = "Juan Pérez Preview", email = "juan@preview.com", onLogout = {})
    }
}