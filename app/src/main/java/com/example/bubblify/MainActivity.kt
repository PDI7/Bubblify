package com.example.bubblify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.ui.theme.BubblifyTheme

import com.example.bubblify.view.UserPage

import com.example.bubblify.view.OtherPage
import com.example.bubblify.viewmodel.OtherViewModel

import com.example.bubblify.viewmodel.UserViewModel
import com.example.bubblify.viewmodel.ProfilePageViewModel
import com.example.bubblify.viewmodel.SetActivityViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()

    private val otherViewModel: OtherViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Navigation Core
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "other") {
                composable("user") { UserPage(userViewModel, navController) }
                composable("other") { OtherPage(otherViewModel, navController) }
                /* nAdd your page here */
            }
        }
    }
}
