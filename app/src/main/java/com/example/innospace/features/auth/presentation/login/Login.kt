package com.example.innospace.features.auth.presentation.login

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.innospace.core.networking.SessionManager
import com.example.innospace.core.ui.theme.InnoSpaceTheme
import com.example.innospace.features.auth.domain.models.User
import com.example.innospace.features.auth.domain.repositories.AuthRepository
import com.example.innospace.features.student.domain.repositories.StudentRepository
import com.example.innospace.shared.models.Opportunity
import com.example.innospace.shared.models.Project
import com.example.innospace.shared.models.StudentProfile

@Composable
fun Login(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit
) {
    val emailState = viewModel.email.collectAsState()
    val passwordState = viewModel.password.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    val isPasswordVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "InnoSpace",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { viewModel.updateEmail(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { viewModel.updatePassword(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Contraseña") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    visualTransformation = if (isPasswordVisible.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible.value = !isPasswordVisible.value
                        }) {
                            Icon(
                                imageVector = if (isPasswordVisible.value) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = null
                            )
                        }
                    },
                    singleLine = true
                )

                errorMessage.value?.let { message ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.login() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isLoading.value
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator()
                    } else {
                        Text("Iniciar Sesión")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = onNavigateToRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }
        }
    }
}

@Composable
fun PreviewLoginViewModel(): LoginViewModel {
    val context = LocalContext.current
    return remember {
        val mockAuthRepository = object : AuthRepository {
            override suspend fun login(email: String, password: String): User? = null
            override suspend fun register(
                name: String,
                email: String,
                password: String,
                accountType: String
            ): User? = null
        }
        val mockStudentRepository = object : StudentRepository {
            override suspend fun getOpportunityById(id: Long): Opportunity? = null
            override suspend fun getProjectsByStudentId(studentId: Long): List<Project> =
                emptyList()

            override suspend fun getStudentProfileById(profileId: Long): StudentProfile? = null
            override suspend fun createStudentProfile(userId: Long, name: String): StudentProfile? =
                null

            override suspend fun createProject(
                studentId: Long,
                title: String,
                description: String
            ): Project? = null

            override suspend fun getProjectById(id: Long): Project? = null
        }

        val mockSessionManager = SessionManager(context)

        LoginViewModel(
            authRepository = mockAuthRepository,
            studentRepository = mockStudentRepository,
            sessionManager = mockSessionManager
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    InnoSpaceTheme {
        Login(
            viewModel = PreviewLoginViewModel(),
            onNavigateToRegister = {}
        )
    }
}