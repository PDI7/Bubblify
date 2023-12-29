package com.example.bubblify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.view.BubbleListPage
import com.example.bubblify.view.BubblePage
import com.example.bubblify.view.LoginPage
import com.example.bubblify.view.MorePage
import com.example.bubblify.view.ProfilePage
import com.example.bubblify.view.SetActivityPage
import com.example.bubblify.view.SignUpPage
import com.example.bubblify.viewmodel.BubbleListViewModel
import com.example.bubblify.viewmodel.BubbleViewModel
import com.example.bubblify.viewmodel.MoreViewModel
import com.example.bubblify.viewmodel.ProfileViewModel
import com.example.bubblify.viewmodel.SetActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private val moreViewModel: MoreViewModel by viewModels()
    private val setActivityViewModel: SetActivityViewModel by viewModels()
    private val bubbleListViewModel: BubbleListViewModel by viewModels()
    private val bubbleViewModel: BubbleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Navigation Core
            val navController = rememberNavController()
            val mainState = MainState(navController)
            NavHost(navController = navController, startDestination = "login") {
                composable("profile") { ProfilePage(profileViewModel, navController) }
                composable("login") { LoginPage(mainState) }
                composable("signUp") { SignUpPage(mainState) }
                composable("more") { MorePage(moreViewModel, navController) }
                composable("setActivity") { SetActivityPage(setActivityViewModel, navController) }
                composable("bubbleList") { BubbleListPage(bubbleListViewModel, navController) }
                composable("bubbleMain") { BubblePage(bubbleViewModel, navController) }
            }
        }
    }
}
