package com.example.bubblify

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

@Stable
class MainState(
    val navController: NavHostController,
) {

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

}

