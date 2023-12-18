package com.example.bubblify.view

 import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
 import androidx.navigation.NavController
 import com.example.bubblify.model.BeerResponse
import com.example.bubblify.model.User
import com.example.bubblify.ui.theme.BubblifyTheme
import com.example.bubblify.viewmodel.UserViewModel


@Composable
fun UserPage(viewModel: UserViewModel, navController: NavController) {
    val users by viewModel.users.observeAsState(null)
    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    Column {
        if (users == null) {
            // Show loading indicator or placeholder
            Text(text = "Loading...")
        } else {
            // Display the list of credit cards
            UserItem(users!!)
        }
    }
}


@Composable
fun UserItem(user: BeerResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Brand: ${user.brand}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text =  "Name: ${user.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rate: ${user.alcohol}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}