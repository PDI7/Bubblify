package com.example.bubblify.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bubblify.viewmodel.BubbleListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleListPage(bubbleListViewModel: BubbleListViewModel, navController: NavController) {

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

        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 38.dp,
                    y = 137.dp
                )
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp)
            ) {
                Text("Erasmus Friends")
        }
        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 38.dp,
                    y = 218.dp
                )
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp)
            ) {
                Text("Family Chausson")
        }
        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 38.dp,
                    y = 299.dp
                )
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp)
        ) {
            Text("Jotac People")
        }
        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 38.dp,
                    y = 380.dp
                )
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp)
        ) {
            Text("+")
        }

        NavBarUI()

    }
}

/*
@Preview
@Composable
private fun LoginScreenPreview() {
    BubbleListPage(bubbleListViewModel = BubbleListViewModel())
}
*/