package com.example.move_application.presentation.navigations

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home")
    data object Details : Screen(route = "details/{contentId}") {
        fun createRoute(contentId: String) = "details/$contentId"
    }
}
