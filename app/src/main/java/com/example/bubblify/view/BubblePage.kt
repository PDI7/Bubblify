package com.example.bubblify.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import com.example.bubblify.R
import com.example.bubblify.model.Activity
import com.example.bubblify.model.ActivityIcon
import com.example.bubblify.ui.theme.Purple40
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.BubbleViewModel
import com.example.bubblify.viewmodel.SharedHomeBubbleViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubblePage(bubbleViewModel: BubbleViewModel, navController: NavHostController, sharedHomeBubbleViewModel: SharedHomeBubbleViewModel, groupId: String?) {

    // Add the listener
    val activityList by bubbleViewModel.activities.observeAsState(null)
    val home = listOf(200.dp, 150.dp, 180.dp, 200.dp, 150.dp)

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        if(groupId != null)
            bubbleViewModel.fetchActivities(groupId)
        else
            Log.d("Bonsoir non", groupId.toString())
    }

    var selectedActivity by remember { mutableStateOf<Activity?>(null) }

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
                IconButton(onClick = { navController.navigate("groupSettings") }) {
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
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 70.dp),
                columns = GridCells.Fixed(3), content = {
                    items(activityList!!.size) { index ->
                        Bubble(home[index], activityList!![index]) {
                            selectedActivity = activityList!![index]
                        }
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
fun Bubble(bubbleSize: Dp = 200.dp, activity: Activity, onBubbleClick: () -> Unit) {
    var isDialogOpen by remember { mutableStateOf(false) }

    Button(onClick = { isDialogOpen = !isDialogOpen },
        modifier = Modifier
            .padding(5.dp)
            .size(bubbleSize)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 0..1) {
                Row(
                    modifier = Modifier.weight(2.5f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (i in 0..2) {
                        Image(
                            painter = painterResource(id = R.drawable.profilepic),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .clip(CircleShape)
                                .padding(end = 3.dp)
                        )
                    }
                }
            }
            Image(
                painter = painterResource(id = activity.icon!!.icon),
                contentDescription = "",
                modifier = Modifier.weight(1f),
            )
        }
    }

    if (isDialogOpen) {
        // If the card is open, display the ElevatedCard
        MinimalDialog(activity = activity, onDismissRequest = { isDialogOpen = false },)
    }
}

@Composable
fun MinimalDialog(activity: Activity, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Purple40),
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
                                .padding(end = 3.dp)
                        )
                    }
                }
                Row(
                ) {
                    Image(
                        painter = painterResource(id = activity.icon!!.icon),
                        contentDescription = "",
                        modifier = Modifier
                            .weight(2f)
                            .size(50.dp)
                            .padding(bottom = 20.dp)
                    )
                    Text(
                        text = activity.name,
                        color = Color(0xFFF4ECFF),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .weight(2f)
                    )
                }
            }
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