package com.example.innospace.features.explore.presentation.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ErrorView(error: String?) =
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error: $error", color = MaterialTheme.colorScheme.error)
    }