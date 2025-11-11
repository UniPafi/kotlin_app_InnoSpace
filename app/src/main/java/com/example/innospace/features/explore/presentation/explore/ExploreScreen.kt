package com.example.innospace.features.explore.presentation.explore

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.innospace.features.explore.domain.model.OpportunityCard


@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    navController: NavController,
    userId: Long
) {
    val uiState by viewModel.uiState.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val tabs = listOf("Convocatorias", "Favoritos")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ExploreTabs(tabs, selectedTab) { selectedTab = it }

        SearchBar(searchQuery) { searchQuery = it }

        Crossfade(targetState = uiState) { state ->
            when {
                state.isLoading -> LoadingView()
                state.error != null -> ErrorView(state.error)
                else -> {
                    val favoriteIds = favorites.map { it.id }.toSet()
                    val baseList = if (selectedTab == 0)
                        state.opportunities.map { it to (it.id in favoriteIds) }
                    else
                        favorites.map { it to true }

                    val filteredList = baseList.filter { (opp, _) ->
                        searchQuery.isBlank() ||
                                opp.title.contains(searchQuery, ignoreCase = true) ||
                                opp.companyName.contains(searchQuery, ignoreCase = true) ||
                                opp.category.contains(searchQuery, ignoreCase = true)
                    }

                    OpportunityList(
                        opportunities = filteredList,
                        onFavoriteToggle = viewModel::toggleFavorite,
                        onOpenDetail = { id ->
                            navController.navigate("opportunityDetail/$id/$userId")
                        }
                    )
                }
            }
        }

    }
        }
