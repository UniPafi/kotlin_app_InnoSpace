package com.example.innospace.core.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
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
import com.example.innospace.features.myprojects.presentation.add.AddProjectScreen
import com.example.innospace.features.myprojects.presentation.detail.ProjectDetailScreen
import com.example.innospace.features.myprojects.presentation.edit.EditProjectScreen
import com.example.innospace.features.myprojects.presentation.list.MyProjectsScreen


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
        NavigationItem(Icons.AutoMirrored.Filled.Assignment, "Mis Postulaciones", Route.Applications.route),
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
                    viewModel = hiltViewModel(),
                    userId = userId
                )
            }

            composable(
                route = "opportunityDetail/{id}/{studentId}",
                arguments = listOf(

                    navArgument("id") { type = NavType.LongType },
                    navArgument("studentId") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: return@composable
                val studentId = backStackEntry.arguments?.getLong("studentId") ?: return@composable
                OpportunityDetailScreen(
                    navController = navController,
                    opportunityId = id,
                    viewModel = hiltViewModel(),
                    onBack = {navController.popBackStack()},
                    studentId = studentId
                )
            }

            composable(Route.MyProjects.route) {
                MyProjectsScreen(
                    viewModel = hiltViewModel(),
                    navController = navController,
                    studentId = userId
                )
            }

            composable(
                route = Route.AddProject.route,
                arguments = listOf(navArgument("studentId") { type = NavType.LongType })
            ) { backStackEntry ->
                val studentId = backStackEntry.arguments?.getLong("studentId") ?: 0L
                AddProjectScreen(
                    viewModel = hiltViewModel(),
                    studentId = studentId,
                    onProjectCreated = {
                        navController.popBackStack()
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Route.ProjectDetail.route,
                arguments = listOf(navArgument("projectId") { type = NavType.LongType })
            ) { backStackEntry ->
                val projectId = backStackEntry.arguments?.getLong("projectId") ?: 0L
                ProjectDetailScreen(
                    viewModel = hiltViewModel(),
                    projectId = projectId,
                    navController = navController
                )
            }

            composable(
                route = Route.EditProject.route,
                arguments = listOf(navArgument("projectId") { type = NavType.LongType })
            ) { backStackEntry ->
                val projectId = backStackEntry.arguments?.getLong("projectId") ?: 0L
                EditProjectScreen(
                    viewModel = hiltViewModel(),
                    projectId = projectId,
                    onProjectUpdated = {
                        navController.popBackStack()
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
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