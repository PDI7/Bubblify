package com.example.bubblify.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import com.example.bubblify.viewmodel.BubbleViewModel


@Composable
fun BubblePage(bubbleViewModel: BubbleViewModel, navController: NavHostController) {

    // Add the listener
    val groups by bubbleViewModel.groups.observeAsState(null)

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        bubbleViewModel.fetchGroups()
        Log.d("Hallo", groups.toString())
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

    val bubbleList = listOf(200.dp, 150.dp, 180.dp, 200.dp, 150.dp)
    val activityList = listOf(
        Activity("Work", "Erasmus Friends", ActivityIcon.WORKING),
        Activity("Eat", "Erasmus Friends", ActivityIcon.EATING),
        Activity("Sleep", "Erasmus Friends", ActivityIcon.SLEEPING)
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3) , content = {
        items(activityList.size){ index ->
            Bubble(bubbleList[index], activityList[index])
        }
    })
}

@Composable
fun Bubble(bubbleSize: Dp = 200.dp, activity: Activity = Activity("Work", "Erasmus Friends", ActivityIcon.WORKING)) {
    Button(onClick = {

    }, modifier = Modifier
        .padding(8.dp)
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
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                    )
                }
            }
            Image(
                painter = painterResource(id = activity.icon.icon),
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