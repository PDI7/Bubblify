package com.example.bubblify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.view.BubbleListPage
import com.example.bubblify.view.LoginPage
import com.example.bubblify.view.MorePage
import com.example.bubblify.view.OtherPage
import com.example.bubblify.view.ProfilePage
import com.example.bubblify.view.SetActivityPage
import com.example.bubblify.view.SignUpPage
import com.example.bubblify.view.UserPage
import com.example.bubblify.viewmodel.BubbleListViewModel
import com.example.bubblify.viewmodel.LoginViewModel
import com.example.bubblify.viewmodel.MoreViewModel
import com.example.bubblify.viewmodel.OtherViewModel
import com.example.bubblify.viewmodel.ProfileViewModel
import com.example.bubblify.viewmodel.SetActivityViewModel
import com.example.bubblify.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val otherViewModel: OtherViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val moreViewModel: MoreViewModel by viewModels()
    private val setActivityViewModel: SetActivityViewModel by viewModels()
    private val bubbleListViewModel: BubbleListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Navigation Core
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("user") { UserPage(userViewModel, navController) }
                composable("other") { OtherPage(otherViewModel, navController) }
                composable("profile") { ProfilePage(profileViewModel, navController) }
                composable("login") { LoginPage(loginViewModel, navController) }
                composable("signUp") { SignUpPage(navController) }
                composable("more") { MorePage(moreViewModel, navController) }
                composable("setActivity") { SetActivityPage(setActivityViewModel, navController) }
                composable("bubbleList") { BubbleListPage(bubbleListViewModel, navController) }
            }
        }
    }
}
