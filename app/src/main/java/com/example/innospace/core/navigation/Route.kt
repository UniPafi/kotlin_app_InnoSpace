package com.example.innospace.core.navigation

sealed class Route(val route: String) {
    object Login : Route("login")
    object Register : Route("register")
    object StudentMain : Route("student_main/{userId}/{name}/{email}") {
        fun createRoute(userId: Long, name: String, email: String) = "student_main/$userId/$name/$email"
    }

    object Explore : Route("explore")
    object MyProjects : Route("my_projects")
    object Applications : Route("applications")
    object Profile : Route("profile")
    object AddProject : Route("add_project/{studentId}") {
        fun createRoute(studentId: Long) = "add_project/$studentId"
    }
    object ProjectDetail : Route("project_detail/{projectId}") {
        fun createRoute(projectId: Long) = "project_detail/$projectId"
    }

    object EditProject : Route("edit_project/{projectId}") {
        fun createRoute(projectId: Long) = "edit_project/$projectId"
    }
}