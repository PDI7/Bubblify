package com.example.bubblify.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bubblify.model.Group
import com.example.bubblify.model.Reference
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import com.example.bubblify.viewmodel.SharedHomeBubbleViewModel
import com.google.firebase.firestore.DocumentReference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(homeViewModel: HomeViewModel, navController: NavController, sharedHomeBubbleViewModel: SharedHomeBubbleViewModel) {

    // Add the listener
    val groups by homeViewModel.groups.observeAsState(null)

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        delay(500)
        homeViewModel.fetchGroups()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CenterAlignedTopAppBar(
            title = { Text("Groups", maxLines = 1) },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notification"
                    )
                }
            }
        )

        if (groups == null) {
            // Waiting for the data (and avoid app crash)
            Text(text = "Loading...")
        } else {
            // Display the list of groups
            groups!!.forEach { group ->
                GroupItem(
                    sharedHomeBubbleViewModel = sharedHomeBubbleViewModel,
                    group = group.data,
                    groupId = group.reference.id,
                    navController = navController,
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 38.dp,
                            y = 137.dp + (groups!!.indexOf(group) * 81).dp // Adapt the position to the number of groups
                        )
                )
            }
            // Display an empty button to add a new group
            FilledTonalButton(
                onClick = {
                    homeViewModel.createGroup()
                    navController.navigate("home")
                          },
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(
                        x = 38.dp,
                        y = 137.dp + (groups!!.size * 81).dp // Adapt the position to the number of groups
                    )
                    .requiredWidth(width = 285.dp)
                    .requiredHeight(height = 60.dp)
            ) {
                Text("+")
            }
        }
        NavigationBar(navController = navController)
    }
}

// Group Button
@Composable
fun GroupItem(group: Group, groupId: String, modifier: Modifier = Modifier, navController: NavController, sharedHomeBubbleViewModel: SharedHomeBubbleViewModel) {
    FilledTonalButton(
        onClick = {
            sharedHomeBubbleViewModel.addGroup(group, groupId)
            navController.navigate("bubbleMain/$groupId")
        },
        colors = ButtonDefaults.filledTonalButtonColors(Color(group.color)),
        modifier = modifier
            .requiredWidth(width = 285.dp)
            .requiredHeight(height = 60.dp)
    ) {
        Text(group.name, color = Color.White)
    }
}

/*
@Preview
@Composable
private fun LoginScreenPreview() {
    HomePage(HomeViewModel = HomeViewModel())
}
*/