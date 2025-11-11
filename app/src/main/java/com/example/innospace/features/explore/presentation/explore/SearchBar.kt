package com.example.innospace.features.explore.presentation.explore

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Buscar convocatorias...",
            color = colorScheme.onSurface.copy(alpha = 0.6f)

            ) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null,
            tint = colorScheme.onSurface.copy(alpha = 0.7f)

            ) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colorScheme.onSurface,
            unfocusedTextColor = colorScheme.onSurface,
            focusedBorderColor = colorScheme.primary,
            unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.4f),
            cursorColor = colorScheme.primary
        )
    )
}