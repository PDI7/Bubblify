package com.example.bubblify.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.model.Reference
import com.example.bubblify.model.User
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.AddMembersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMembersPage(
    mainState: MainState,
    viewModel: AddMembersViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchResults by viewModel.searchResults.observeAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<Reference<User>?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = { mainState.navigate("groupSettings") }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "ArrowBack"
                    )
                }
            },
            modifier = Modifier.align(Alignment.TopCenter),
            title = { Text("Add members") }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {
            Text(text = "Search to add a member")
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.searchMembers(searchQuery.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = {
                    Text(text = "Enter member username")
                }
            )

            LazyColumn {
                items(searchResults) { user ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Display user details
                        Text(text = user.data.username)


                        // Add remove icon button
                        Icon(
                            imageVector = Icons.Default.PersonAddAlt,
                            contentDescription = "Add",
                            tint = Color.Green,
                            modifier = Modifier
                                .clickable {
                                    Log.d("user.id", user.reference.id)
                                    selectedUser = user
                                }
                                .padding(8.dp)
                        )
                    }
                    selectedUser?.let { user ->
                        AddUserDialog(
                            username = user.data.username,
                            onConfirm = {
                                // Handle OK button action
                                //viewModel.addMemberToGroup(user.reference, )
                                selectedUser = null
                            },
                            onCancel = {
                                // Handle Cancel button action
                                selectedUser = null
                            }
                        )
                    }
                }
            }
        }
        NavigationBar(navController = mainState.navController)
    }
}

@Composable
fun AddUserDialog(username: String, onConfirm: () -> Unit, onCancel: () -> Unit) {

        AlertDialog(
            shape = RoundedCornerShape(0.dp), // Adjust the corner radius as needed
            containerColor = Color.White,
            onDismissRequest = onCancel,
            title = { Text(username) },
            text = { Text(
                text = "Do you want to add this user?",
                ) },
            confirmButton = {
                TextButton(
                    onClick = onConfirm,
                ) {
                    Text(
                        text = "OK",
                        color = Color(4282692263)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onCancel,
                ) {
                    Text(
                        text = "Cancel",
                        color = Color(4282692263)
                    )
                }
            },
            modifier = Modifier.padding(0.dp)
        )

}

