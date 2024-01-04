package com.example.bubblify.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.model.Activity
import com.example.bubblify.ui.theme.Purple40
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.AddActivitiesViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivitiesPage(
    mainState: MainState,
    groupId: String?,
    viewModel: AddActivitiesViewModel = hiltViewModel()
) {
    val activities by viewModel.activities.observeAsState(initial = emptyList())
    var selectedActivity by remember { mutableStateOf<Activity?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (groupId != null) {
            viewModel.fetchActivities(groupId)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = { mainState.navigate("groupSettings/$groupId") },
                    modifier = Modifier.testTag("navigationBack")) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "ArrowBack"
                    )
                }
            },
            modifier = Modifier.align(Alignment.TopCenter),
            title = { Text("Add activities") }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {

            LazyColumn {
                items(activities) { activity ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = activity.icon!!.icon),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.Black, CircleShape)
                                .background(Purple40)
                                .padding(4.dp),
                        )

                        // Display user details
                        Text(
                            text = activity.name,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        )

                        Icon(
                            imageVector = Icons.Default.AddCircleOutline,
                            contentDescription = "AddActivity",
                            tint = Color.Green,
                            modifier = Modifier
                                .clickable {
                                    selectedActivity = activity
                                }
                                .padding(8.dp)
                                .testTag("AddActivityIcon")
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    selectedActivity?.let { activity ->
                        AddActivityDialog(
                            name = activity.name,
                            onConfirm = {
                                // Handle OK button action
                                if (groupId != null) {
                                    viewModel.addActivityToGroup(activity, groupId) { result ->
                                        if (result != null) {
                                            mainState.navigate("groupSettings/$groupId")
                                        } else {
                                            showPopup = true
                                        }
                                    }
                                }
                                selectedActivity = null
                            },
                            onCancel = {
                                // Handle Cancel button action
                                selectedActivity = null
                            }
                        )
                    }
                }
            }
            if (showPopup) {
                PopupActivityError("Too many activities (max 5) !")

                LaunchedEffect(Unit) {
                    delay(5000) // Delay for 5 seconds
                    showPopup = false
                }
            }
        }
        NavigationBar(mainState.navController)
    }
}

@Composable
fun AddActivityDialog(name: String, onConfirm: () -> Unit, onCancel: () -> Unit) {

    AlertDialog(
        shape = RoundedCornerShape(0.dp), // Adjust the corner radius as needed
        containerColor = Color.White,
        onDismissRequest = onCancel,
        title = { Text(name) },
        text = { Text(
            text = "Do you want to add this activity?",
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

@Composable
fun PopupActivityError(message: String) {
    // Your popup UI here
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red.copy(alpha = 0.8f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.White
        )
    }
}