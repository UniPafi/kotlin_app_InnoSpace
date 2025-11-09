package com.example.innospace.features.explore.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innospace.features.explore.data.local.models.OpportunityEntity
import com.example.innospace.features.explore.data.local.repositories.OpportunityLocalRepository
import com.example.innospace.features.explore.domain.model.OpportunityCard
import com.example.innospace.features.explore.domain.usecases.GetAllOpportunitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getAllOpportunitiesUseCase: GetAllOpportunitiesUseCase,
    private val localRepository: OpportunityLocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState

    private val _favorites = MutableStateFlow<List<OpportunityCard>>(emptyList())
    val favorites: StateFlow<List<OpportunityCard>> = _favorites

    init {
        loadOpportunities()
        observeFavorites()
    }
    private fun loadOpportunities() {
        viewModelScope.launch {
            try {
                _uiState.value = ExploreUiState(isLoading = true)
                val data = getAllOpportunitiesUseCase()
                _uiState.value = ExploreUiState(opportunities = data)
            } catch (e: Exception) {
                _uiState.value = ExploreUiState(error = e.message ?: "Error desconocido")
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            localRepository.getAllFavorites().collect { list ->
                _favorites.value = list.map { entity ->
                    OpportunityCard(
                        id = entity.id,
                        title = entity.title,
                        summary = entity.summary,
                        category = entity.category,
                        companyName = entity.companyName
                    )
                }
            }
        }
    }

    fun toggleFavorite(opportunity: OpportunityCard, companyDesc: String? = null, photoUrl: String? = null) {
        viewModelScope.launch {
            val exists = localRepository.isFavorite(opportunity.id)
            val entity = OpportunityEntity(
                id = opportunity.id,
                title = opportunity.title,
                summary = opportunity.summary,
                description = opportunity.summary,
                category = opportunity.category,
                requirements = "",
                companyName = opportunity.companyName,
                companyDescription = companyDesc,
                companyLocation = null,
                companyPhotoUrl = photoUrl
            )
            if (exists) localRepository.removeFavorite(entity)
            else localRepository.addFavorite(entity)
        }
    }

    suspend fun isFavorite(id: Long): Boolean = localRepository.isFavorite(id)
}

data class ExploreUiState(
    val isLoading: Boolean = false,
    val opportunities: List<OpportunityCard> = emptyList(),
    val error: String? = null
)
