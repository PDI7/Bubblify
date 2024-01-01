package com.example.bubblify.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.viewmodel.AddMembersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMembersPage(
    mainState: MainState,
    viewModel: AddMembersViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchResults by viewModel.searchResults.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchMembers(searchQuery.text) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = {
                Text(text = "Enter member name")
            }
        )

        Button(
            onClick = {
                viewModel.searchMembers(searchQuery.text)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Search Members")
        }

        LazyColumn {
            items(searchResults) { user ->
                // Display user details as needed
                Text(text = "Username: ${user.username}")
            }
        }
    }

}

