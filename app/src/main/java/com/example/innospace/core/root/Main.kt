package com.example.innospace.core.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.innospace.core.navigation.Route
import com.example.innospace.features.explore.presentation.detail.OpportunityDetailScreen
import com.example.innospace.features.explore.presentation.explore.ExploreScreen


data class NavigationItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

@Composable
fun Main(userId: Long, name: String, email: String, onLogout: () -> Unit) {
    val navController = rememberNavController()
    val selectedIndex = remember { mutableIntStateOf(0) }

    val navigationItems = listOf(
        NavigationItem(Icons.Default.Explore, "Explorar", Route.Explore.route),
        NavigationItem(Icons.Default.Work, "Mis Proyectos", Route.MyProjects.route),
        NavigationItem(Icons.Default.Assignment, "Mis Postulaciones", Route.Applications.route),
        NavigationItem(Icons.Default.Person, "Perfil", Route.Profile.route)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedIndex.intValue,
                        onClick = {
                            selectedIndex.intValue = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) {  paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.Explore.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.Explore.route) {
                ExploreScreen(
                    navController = navController,
                    viewModel = hiltViewModel()
                )
            }


            composable(
                route = "opportunityDetail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: return@composable
                OpportunityDetailScreen(
                    navController = navController,
                    opportunityId = id,
                    viewModel = hiltViewModel(),
                    onBack = {navController.popBackStack()}
                )
            }

            composable(Route.MyProjects.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sección Mis Proyectos (en desarrollo)")
                }
            }

            composable(Route.Applications.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sección Mis Postulaciones (en desarrollo)")
                }
            }

            composable(Route.Profile.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sección Perfil (en desarrollo)")
                }
            }
        }
    }
}