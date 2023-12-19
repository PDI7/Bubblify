package com.example.bubblify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.view.BubblePage
import com.example.bubblify.view.GroupPage
import com.example.bubblify.view.LoginPage
import com.example.bubblify.view.UserPage
import com.example.bubblify.viewmodel.GroupViewModel
import com.example.bubblify.viewmodel.LoginViewModel
import com.example.bubblify.view.OtherPage
import com.example.bubblify.viewmodel.BubbleViewModel
import com.example.bubblify.viewmodel.OtherViewModel
import com.example.bubblify.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()
    private val otherViewModel: OtherViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val groupViewModel: GroupViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val bubbleViewModel: BubbleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Navigation Core
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "other") {
                composable("user") { UserPage(userViewModel, navController) }
                composable("other") { OtherPage(otherViewModel, navController) }
                composable("group") { GroupPage(groupViewModel, navController) }
                composable("login") { LoginPage(loginViewModel, navController) }
                composable("bubble") { BubblePage(bubbleViewModel, navController) }
                /* Add your page here */
            }
        }
    }
}
