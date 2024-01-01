package com.example.bubblify.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bubblify.MainState
import com.example.bubblify.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    mainState: MainState,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController, sharedBubbleListBubbleViewModel: SharedBubbleListBubbleViewModel
) {

    val groups by viewModel.groups.observeAsState(null)

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        viewModel.fetchGroups()
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
            // Display the list of credit cards
            groups!!.forEach { group ->
                GroupItem(
                    sharedBubbleListBubbleViewModel = sharedBubbleListBubbleViewModel,
                    group = group,
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
                onClick = { navController.navigate("bubbleList") },
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


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { viewModel.createGroup() },
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        border = BorderStroke(0.5.dp, Color.Black),
    ) {
        Text(
            text = "+",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )
    }
}