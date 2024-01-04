package com.example.bubblify.view

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.model.Activity
import com.example.bubblify.model.Reference
import com.example.bubblify.model.User
import com.example.bubblify.ui.theme.Purple40
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.GroupSettingsViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupSettingsPage(
    mainState: MainState,
    groupId: String?,
    viewModel: GroupSettingsViewModel = hiltViewModel()
) {
    var selectedChoice by remember { mutableStateOf("Members") }
    val users by viewModel.users.observeAsState(initial = emptyList())
    val activities by viewModel.activities.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        delay(500)
        if (groupId != null) {
            viewModel.fetchUsers(groupId)
            viewModel.fetchActivities(groupId)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .then(Modifier.testTag("backArrow")),
                        onClick = { mainState.navigate("bubbleMain/$groupId")
                        }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                },
                title = { Text("Group Settings") },
                actions = {
                    IconButton(onClick = { /* TODO : Color change popup */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Palette,
                            contentDescription = "Palette"
                        )
                    }
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { selectedChoice = "Members" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(0.dp))
                            .background(Color.Transparent)
                            .height(35.dp)
                            .then(Modifier.testTag("membersTab")),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                        border = BorderStroke(0.dp, Color.Transparent),
                    ) {
                        Text(
                            text = "Members",
                            color = if (selectedChoice == "Members") Color.Black else Color.LightGray,
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(if (selectedChoice == "Members") Color.Black else Color.LightGray)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { selectedChoice = "Activities" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(0.dp))
                            .background(Color.Transparent)
                            .height(35.dp)
                            .then(Modifier.testTag("activitiesTab")),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(0.dp, Color.Transparent),
                    ) {
                        Text(
                            text = "Activities",
                            color = if (selectedChoice == "Activities") Color.Black else Color.LightGray,
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(if (selectedChoice == "Activities") Color.Black else Color.LightGray)
                    )
                }
            }

            when (selectedChoice) {
                "Members" -> DisplayMembersContent(users, mainState, groupId, viewModel)
                "Activities" -> DisplayActivitiesContent(activities, mainState, groupId, viewModel)
            }
        }
        NavigationBar(mainState.navController)
    }
}

@Composable
fun DisplayMembersContent(
    users: List<Reference<User>>,
    mainState: MainState,
    groupId: String?,
    viewModel: GroupSettingsViewModel
) {
    var selectedUser by remember { mutableStateOf<Reference<User>?>(null) }

    LazyColumn (
        modifier = Modifier.padding(16.dp)
    ) {
        items(users) { user ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (users == null) {
                    // Waiting for the data (and avoid app crash)
                    Text(text = "Loading...")
                } else {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Black, CircleShape)
                            .padding(end = 12.dp)
                    ) {  }

                    Text(
                        text = user.data.username,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp)
                    )

                    Icon(
                        imageVector = Icons.Outlined.PersonRemove,
                        contentDescription = "RemoveMember",
                        tint = Color.Red,
                        modifier = Modifier
                            .clickable {
                                Log.d("user.id", user.reference.id)
                                selectedUser = user
                            }
                            .padding(8.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            selectedUser?.let { user ->
                RemoveMemberDialog(
                    username = user.data.username,
                    onConfirm = {
                        // Handle OK button action
                        if (groupId != null) {
                            viewModel.removeUser(user, groupId)
                        }
                        selectedUser = null
                        mainState.navigate("bubbleMain/$groupId")
                    },
                    onCancel = {
                        // Handle Cancel button action
                        selectedUser = null
                    }
                )
            }
        }
    }

    Button(
        onClick = { mainState.navigate("addMembers/$groupId") },
        modifier = Modifier
            .padding(16.dp)
            .then(Modifier.testTag("addMembersButton")),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
        border = BorderStroke(1.dp, Color.Black),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "+ Add members",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RemoveMemberDialog(username: String, onConfirm: () -> Unit, onCancel: () -> Unit) {

    AlertDialog(
        shape = RoundedCornerShape(0.dp), // Adjust the corner radius as needed
        containerColor = Color.White,
        onDismissRequest = onCancel,
        title = { Text(username) },
        text = { Text(
            text = "Do you want to remove this member?",
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
fun DisplayActivitiesContent(
    activities: List<Reference<Activity>>,
    mainState: MainState,
    groupId: String?,
    viewModel: GroupSettingsViewModel
) {
    var selectedActivity by remember { mutableStateOf<Reference<Activity>?>(null) }

    LazyColumn (
        modifier = Modifier.padding(16.dp)
    ) {
        items(activities) { activity ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (activities == null) {
                    // Waiting for the data (and avoid app crash)
                    Text(text = "Loading...")
                } else {
                    Image(
                        painter = painterResource(id = activity.data.icon!!.icon),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Black, CircleShape)
                            .background(Purple40)
                            .padding(4.dp),
                    )

                    Text(
                        text = activity.data.name,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp)
                    )

                    Icon(
                        imageVector = Icons.Outlined.RemoveCircleOutline,
                        contentDescription = "RemoveActivity",
                        tint = Color.Red,
                        modifier = Modifier
                            .clickable {
                                selectedActivity = activity
                            }
                            .padding(8.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            selectedActivity?.let { activity ->
                RemoveActivityDialog(
                    name = activity.data.name,
                    onConfirm = {
                        // Handle OK button action
                        if (groupId != null) {
                            viewModel.removeActivity(activity)
                        }
                        selectedActivity = null
                        mainState.navigate("bubbleMain/$groupId")
                    },
                    onCancel = {
                        // Handle Cancel button action
                        selectedActivity = null
                    }
                )
            }
        }
    }
    Button(
        onClick = { mainState.navigate("addActivities/$groupId") },
        modifier = Modifier
            .padding(16.dp)
            .testTag("addActivitiesButton"),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color.Black),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "+ Add activities",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RemoveActivityDialog(name: String, onConfirm: () -> Unit, onCancel: () -> Unit) {

    AlertDialog(
        shape = RoundedCornerShape(0.dp), // Adjust the corner radius as needed
        containerColor = Color.White,
        onDismissRequest = onCancel,
        title = { Text(name) },
        text = { Text(
            text = "Do you want to remove this activity?",
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