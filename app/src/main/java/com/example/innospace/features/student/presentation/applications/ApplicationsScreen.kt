package com.example.innospace.features.student.presentation.applications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.innospace.core.ui.theme.InnoSpaceTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsScreen(viewModel: ApplicationsViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Postulaciones") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Aquí se mostrarán tus postulaciones a oportunidades")
        }
    }
}

@HiltViewModel
class ApplicationsViewModel @Inject constructor() : ViewModel() {

}

@Composable
fun ApplicationsScreenPreview() {
    InnoSpaceTheme {
        ApplicationsScreen()
    }
}