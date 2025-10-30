package com.example.innospace.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.innospace.core.root.Main
import com.example.innospace.features.auth.presentation.login.Login
import com.example.innospace.features.auth.presentation.login.LoginViewModel
import com.example.innospace.features.auth.presentation.register.Register
import com.example.innospace.features.auth.presentation.register.RegisterViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Route.Login.route) {

        // LOGIN
        composable(Route.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState.user) {
                uiState.user?.let {
                    navController.navigate(
                        Route.StudentMain.createRoute(
                            userId = it.id,
                            name = it.name ?: it.email,
                            email = it.email
                        )
                    ) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                }
            }

            Login(
                viewModel = viewModel,
                onLoginSuccess = { user ->
                    navController.navigate(
                        Route.StudentMain.createRoute(
                            userId = user.id,
                            name = user.name ?: user.email,
                            email = user.email
                        )
                    ) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Route.Register.route) }
            )
        }

        // REGISTER
        composable(Route.Register.route) {
            val viewModel: RegisterViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState.user) {
                uiState.user?.let {
                    navController.navigate(
                        Route.StudentMain.createRoute(
                            userId = it.id,
                            name = it.name ?: it.email,
                            email = it.email
                        )
                    ) {
                        popUpTo(Route.Register.route) { inclusive = true }
                    }
                }
            }

            Register(
                viewModel = viewModel,
                onRegisterSuccess = { user ->
                    navController.navigate(
                        Route.StudentMain.createRoute(
                            userId = user.id,
                            name = user.name ?: user.email,
                            email = user.email
                        )
                    ) {
                        popUpTo(Route.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Route.Login.route) }
            )
        }

        // MAIN (post-login)
        composable(
            route = Route.StudentMain.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.LongType },
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""

            Main(
                userId = userId,
                name = name,
                email = email,
                onLogout = {
                    navController.navigate(Route.Login.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
