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

@Composable
fun NavigationBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    Box( // Navigation bar at the bottom, this box is NEEDED
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = selectedItem == 0,
                onClick = { selectedItem = 0 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = selectedItem == 1,
                onClick = { selectedItem = 1 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Settings, contentDescription = "More") },
                label = { Text("More") },
                selected = selectedItem == 2,
                onClick = { selectedItem = 2 })
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