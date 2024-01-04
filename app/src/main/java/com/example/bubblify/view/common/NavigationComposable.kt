package com.example.bubblify.view.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBar(navController : NavController) {
    var selectedItem by remember { mutableIntStateOf(1) }
    Box( // Navigation bar at the bottom, this box is NEEDED
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
                onClick = {
                    selectedItem = 0
                    navController.navigate("home")
                },
                modifier = Modifier.testTag("homeButton")
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = currentDestination?.hierarchy?.any { it.route == "profile" } == true,
                onClick = {
                    selectedItem = 1
                    navController.navigate("profile")
                },
                modifier = Modifier.testTag("profileButton")
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Settings, contentDescription = "More") },
                label = { Text("More") },
                selected = currentDestination?.hierarchy?.any { it.route == "more" } == true,
                onClick = {
                    selectedItem = 2
                    navController.navigate("more")
                },
                modifier = Modifier.testTag("moreButton")
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackNavigation(backAction: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("Groups", maxLines = 1) },
        navigationIcon = {
            IconButton(onClick = backAction) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "get back"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackNavigationWithNotificationIcon(onClickBack: () -> Unit, onClickNotification: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("Groups", maxLines = 1) },
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "get back"
                )
            }
        },
        actions = {
            IconButton(onClick = onClickNotification) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notification"
                )
            }
        }
    )
}