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
        composable(Route.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val loginSuccess by viewModel.loginSuccess.collectAsState()
            val user by viewModel.user.collectAsState()

            LaunchedEffect(loginSuccess) {
                if (loginSuccess && user != null) {
                    user?.let {
                        navController.navigate(
                            Route.StudentMain.createRoute(
                                userId = it.id,
                                name = it.name ?: it.email,
                                email = it.email
                            )
                        )
                    }
                }
            }
            Login(
                viewModel = viewModel,
                onNavigateToRegister = { navController.navigate(Route.Register.route) }
            )
        }

        composable(Route.Register.route) {
            val viewModel: RegisterViewModel = hiltViewModel()
            val registerSuccess by viewModel.registerSuccess.collectAsState()
            val user by viewModel.user.collectAsState()

            LaunchedEffect(registerSuccess) {
                if (registerSuccess && user != null) {
                    user?.let {
                        navController.navigate(
                            Route.StudentMain.createRoute(
                                userId = it.id,
                                name = it.name ?: it.email,
                                email = it.email
                            )
                        )
                    }
                }
            }

            Register(
                viewModel = viewModel,
                onNavigateToLogin = { navController.navigate(Route.Login.route) }
            )
        }

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