package com.example.innospace.core.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.innospace.core.navigation.Route
import com.example.innospace.features.student.presentation.applications.ApplicationsScreen
import com.example.innospace.features.student.presentation.explore.ExploreScreen
import com.example.innospace.features.student.presentation.myprojects.AddProjectScreen
import com.example.innospace.features.student.presentation.myprojects.MyProjectsScreen
import com.example.innospace.features.student.presentation.myprojects.ProjectDetailScreen
import com.example.innospace.features.student.presentation.profile.ProfileScreen

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
        NavigationItem(Icons.Default.Business, "Postulaciones", Route.Applications.route),
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
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(item.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.Explore.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.Explore.route) {
                ExploreScreen(viewModel = hiltViewModel())
            }

            composable(Route.MyProjects.route) {
                MyProjectsScreen(
                    viewModel = hiltViewModel(),
                    navController = navController,
                    studentId = userId
                )
            }

            composable(Route.Applications.route) {
                ApplicationsScreen(viewModel = hiltViewModel())
            }

            composable(Route.Profile.route) {
                ProfileScreen(
                    viewModel = hiltViewModel(),
                    userId = userId,
                    name = name,
                    email = email,
                    onLogout = onLogout
                )
            }
            composable(
                route = Route.AddProject.route,
                arguments = listOf(navArgument("studentId") { type = NavType.LongType })
            ) { backStackEntry ->
                val studentId = backStackEntry.arguments?.getLong("studentId") ?: 0L
                AddProjectScreen(
                    studentId = studentId,
                    onProjectCreated = { navController.popBackStack() }
                )
            }
            composable(
                route = Route.ProjectDetail.route,
                arguments = listOf(navArgument("projectId") { type = NavType.LongType })
            ) { backStackEntry ->
                val projectId = backStackEntry.arguments?.getLong("projectId") ?: 0L
                ProjectDetailScreen(
                    projectId = projectId,
                    navController = navController
                )
            }
        }
    }
}