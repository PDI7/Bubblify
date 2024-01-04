package com.example.bubblify.view

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.R
import com.example.bubblify.model.Activity
import com.example.bubblify.ui.theme.Purple40
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.BubbleViewModel
import com.example.bubblify.viewmodel.state.GroupState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubblePage(
    mainState: MainState,
    groupState: GroupState,
    groupId: String?,
    bubbleViewModel: BubbleViewModel = hiltViewModel(),
) {

    // Add the listener
    val activityList by bubbleViewModel.activities.observeAsState(null)
    val home = listOf(200.dp, 150.dp, 180.dp, 200.dp, 150.dp)
    var changeName by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(TextFieldValue("")) }

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        if (groupId != null)
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
                IconButton(onClick = { mainState.navigate("home") }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "ArrowBack"
                    )
                }
            },
            modifier = Modifier.align(Alignment.TopCenter),
            title = { groupState.group?.let {
                Text(
                    it.name,
                    maxLines = 1,
                    modifier = Modifier.clickable { changeName = true }
                )
            } },
            actions = {
                IconButton(onClick = { mainState.navigate("groupSettings/$groupId") }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
        )
        if (activityList == null) {
            // Waiting for the data (and avoid app crash)
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary
            )
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

        if (changeName) {
            ChangeNameDialog(
                newName = newName,
                onNewNameChange = { newName = it },
                onConfirm = {
                    // Handle OK button action
                    if (groupId != null) {
                        bubbleViewModel.changeGroupName(newName.text, groupState.group!!, groupId)
                    }
                    changeName = false
                    mainState.navigate("home")
                },
                onCancel = {
                    // Handle Cancel button action
                    changeName = false
                }
            )
        }

        FilledTonalButton(
            onClick = { mainState.navigate("setActivity/$groupId") },
            modifier = Modifier
                .padding(bottom = 120.dp)
                .align(alignment = Alignment.BottomCenter)
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            border = BorderStroke(1.dp, Color.Black),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "+ Set my activity",
                textAlign = TextAlign.Center
            )
        }

        NavigationBar(mainState.navController)
    }

}

@Composable
fun Bubble(bubbleSize: Dp = 200.dp, activity: Activity, onBubbleClick: () -> Unit) {
    var isDialogOpen by remember { mutableStateOf(false) }

    Button(
        onClick = { isDialogOpen = !isDialogOpen },
        modifier = Modifier
            .padding(5.dp)
            .size(bubbleSize)
    ) {
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
        MinimalDialog(activity = activity, onDismissRequest = { isDialogOpen = false })
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeNameDialog(
    newName: TextFieldValue,
    onNewNameChange: (TextFieldValue) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {

    AlertDialog(
        shape = RoundedCornerShape(0.dp), // Adjust the corner radius as needed
        containerColor = Color.White,
        onDismissRequest = onCancel,
        title = { Text(text = "Change the name") },
        text = { TextField(
            value = newName,
            onValueChange = onNewNameChange,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .drawBehind {
                    val borderSide = 2.dp.toPx()
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSide
                    )
                }
                .padding(0.dp)
        ) },
        confirmButton = {
            TextButton(
                onClick = onConfirm,) {
                Text(
                    text = "OK",
                    color = Color(4282692263)
                )
            } },
        dismissButton = {
            TextButton(
                onClick = onCancel,) {
                Text(
                    text = "Cancel",
                    color = Color(4282692263)
                )
            } },
        modifier = Modifier.padding(0.dp)
    )
}
