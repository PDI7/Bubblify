package com.example.bubblify.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bubblify.MainState
import com.example.bubblify.model.Group
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.HomeViewModel
import com.example.bubblify.viewmodel.state.GroupState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    mainState: MainState,
    groupState: GroupState,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    // Add the listener
    val groups by homeViewModel.groups.observeAsState(null)

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        delay(500)
        homeViewModel.fetchGroups()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            // Display the list of groups
            groups!!.forEach { group ->
                GroupItem(
                    groupState = groupState,
                    group = group.data,
                    groupId = group.reference.id,
                    navController = mainState.navController,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Display an empty button to add a new group
            FilledTonalButton(
                onClick = {
                    homeViewModel.createGroup()
                    mainState.navigate("home")
                },
                colors = ButtonDefaults.filledTonalButtonColors(Color.White),
                modifier = Modifier
                    .offset(
                        x = 23.dp
                    )
                    .padding(16.dp, 0.dp)
                    .requiredWidth(width = 285.dp)
                    .requiredHeight(height = 60.dp),
                border = BorderStroke(1.dp, Color.Black),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("+")
            }
            Spacer(modifier = Modifier.height(100.dp))
        }

    }
    NavigationBar(mainState.navController)
}

// Group Button
@Composable
fun GroupItem(
    group: Group,
    groupId: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    groupState: GroupState
) {
    FilledTonalButton(
        onClick = {
            groupState.addGroup(group)
            navController.navigate("bubbleMain/$groupId")
        },
        colors = ButtonDefaults.filledTonalButtonColors(Color(group.color)),

        modifier = Modifier
            .offset(
                x = 38.dp,
            )
            .requiredWidth(width = 285.dp)
            .requiredHeight(height = 60.dp),
        border = BorderStroke(1.dp, Color.Black),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(group.name, color = Color.White)
    }
}