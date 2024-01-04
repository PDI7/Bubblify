package com.example.bubblify

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bubblify.view.AddActivitiesPage
import com.example.bubblify.view.AddMembersPage
import com.example.bubblify.view.BubblePage
import com.example.bubblify.view.GroupSettingsPage
import com.example.bubblify.view.HomePage
import com.example.bubblify.view.LoginPage
import com.example.bubblify.view.MorePage
import com.example.bubblify.view.ProfilePage
import com.example.bubblify.view.SetActivityPage
import com.example.bubblify.view.SignUpPage
import com.example.bubblify.viewmodel.state.GroupState

@Composable
fun BubblifyApp(navController: NavHostController = rememberNavController()) {
    val mainState = MainState(navController)
    val groupState = GroupState()
    NavHost(navController = navController, startDestination = "login") {
        composable("profile") { ProfilePage(mainState) }
        composable("login") { LoginPage(mainState) }
        composable("signUp") { SignUpPage(mainState) }
        composable("more") { MorePage(mainState) }
        composable(
            "setActivity/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })

        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            SetActivityPage(mainState, groupId)

        }
        composable("home") {
            HomePage(
                mainState,
                groupState
            )
        }
        composable(
            "bubbleMain/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            BubblePage(
                mainState,
                groupState,
                groupId
            )
        }
        composable(
            "groupSettings/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            GroupSettingsPage(mainState, groupId)
        }
        composable(
            "addMembers/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            AddMembersPage(mainState, groupId)
        }

        composable(
            "addActivities/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            AddActivitiesPage(mainState, groupId)
        }
    }
}
