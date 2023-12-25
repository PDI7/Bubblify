package com.example.bubblify.view

import android.graphics.Color.parseColor
import androidx.annotation.ColorLong
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.bubblify.model.Group
import com.example.bubblify.viewmodel.BubbleListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleListPage(bubbleListViewModel: BubbleListViewModel, navController: NavController) {

    // Add the listener
    val groups by bubbleListViewModel.groups.observeAsState(null)

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        bubbleListViewModel.fetchGroups()
    }


    Box(
        modifier = Modifier
            .requiredWidth(width = 360.dp)
            .requiredHeight(height = 640.dp)
            .background(color = Color.White)
    ) {

        CenterAlignedTopAppBar(
            title = { Text("Groups", maxLines = 1) },
            navigationIcon = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "get back"
                    )
                }
            },
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
                    group = group,
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
                onClick = { /*TODO*/ },
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
        NavBarUI()
    }
}

// Group Button
@Composable
fun GroupItem(group: Group, modifier: Modifier = Modifier) {
    FilledTonalButton(
        onClick = { /*TODO*/ },
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
    BubbleListPage(bubbleListViewModel = BubbleListViewModel())
}
*/