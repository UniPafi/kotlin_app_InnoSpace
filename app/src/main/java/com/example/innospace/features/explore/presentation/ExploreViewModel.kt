package com.example.innospace.features.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreViewModel @Inject constructor() : ViewModel() {

    var uiState = ExploreUiState()
        private set

    init {
        loadExploreData()
    }

    private fun loadExploreData() {
        viewModelScope.launch {
            // Aquí más adelante podrías hacer la llamada al backend
            uiState = uiState.copy(isLoading = false)
        }
    }
}

data class ExploreUiState(
    val isLoading: Boolean = true,
    val error: String? = null
)