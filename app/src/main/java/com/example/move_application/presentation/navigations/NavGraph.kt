package com.example.move_application.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.move_application.presentation.details.DetailScreen
import com.example.move_application.presentation.home.HomeScreen



@Composable
fun WarchAppNavigation(navController: NavHostController? = null) {
    val controller = navController ?: rememberNavController()

    NavHost(
        navController = controller,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetails = { contentId ->
                    controller.navigate(Screen.Details.createRoute(contentId))
                }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(name = "contentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            DetailScreen(
                contentId = backStackEntry.arguments?.getString("contentId") ?: "",
                contentType = "movie",
                onNavigateBack = { controller.popBackStack() }
            )
        }
    }
}


