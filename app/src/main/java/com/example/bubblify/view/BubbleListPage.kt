package com.example.bubblify.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bubblify.view.common.BackNavigationWithNotificationIcon
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.BubbleListViewModel

@Composable
fun BubbleListPage(bubbleListViewModel: BubbleListViewModel, navController: NavController) {

    Box(
        modifier = Modifier
            .requiredWidth(width = 360.dp)
            .requiredHeight(height = 640.dp)
            .background(color = Color.White)
    ) {

        BackNavigationWithNotificationIcon(
            onClickBack = { /* do something */ },
            onClickNotification = { /* do something */ }
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

        NavigationBar()

    }
}

/*
@Preview
@Composable
private fun LoginScreenPreview() {
    BubbleListPage(bubbleListViewModel = BubbleListViewModel())
}
*/