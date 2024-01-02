package com.example.bubblify.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bubblify.R
import com.example.bubblify.model.Activity
import com.example.bubblify.model.ActivityIcon
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.BubbleViewModel
import com.example.bubblify.viewmodel.SharedHomeBubbleViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubblePage(bubbleViewModel: BubbleViewModel, navController: NavHostController, sharedHomeBubbleViewModel: SharedHomeBubbleViewModel) {

    // Add the listener
    val activityList by bubbleViewModel.activities.observeAsState(null)
    val home = listOf(200.dp, 150.dp, 180.dp, 200.dp, 150.dp)

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        bubbleViewModel.fetchActivities()
        Log.d("Hallo", activityList.toString())
    }

    /*
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Bubble(100.dp)
        Bubble(100.dp)
        Bubble(100.dp)
    }
    */

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "ArrowBack"
                    )
                }
            },
            modifier = Modifier.align(Alignment.TopCenter),
            title = { sharedHomeBubbleViewModel.group?.let { Text(it.name, maxLines = 1) } },
            actions = {
                IconButton(onClick = { /* Open Edit Popup */ }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
        )
        if (activityList == null) {
            // Waiting for the data (and avoid app crash)
            Text(text = "Loading...")
        } else {
            LazyVerticalGrid(
                modifier = Modifier.align(Alignment.Center),
                columns = GridCells.Fixed(3), content = {
                    items(activityList!!.size) { index ->
                        Bubble(home[index], activityList!![index])
                    }
                })
        }

        FilledTonalButton(
            onClick = { navController.navigate("setActivity") },
            modifier = Modifier
                .padding(bottom = 120.dp)
                .align(alignment = Alignment.BottomCenter)
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp)
        ) {
            Text("+ Set my activity")
        }

        NavigationBar(navController = navController)
    }
}

@Composable
fun Bubble(bubbleSize: Dp = 200.dp, activity: Activity) {
    Button(onClick = {

    }, modifier = Modifier
        .padding(5.dp)
        .size(bubbleSize)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.weight(2.5f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0..4) {
                    Image(
                        painter = painterResource(id = R.drawable.profilepic),
                        contentDescription = "",
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                    )
                }
            }
            Image(
                painter = painterResource(id = activity.icon!!.icon),
                contentDescription = "",
                modifier = Modifier.weight(1f),
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun BubblePagePreview(){
    BubblePage(BubbleViewModel(), rememberNavController())
}
*/