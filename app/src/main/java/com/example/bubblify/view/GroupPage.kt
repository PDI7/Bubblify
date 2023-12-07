package com.example.bubblify.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.bubblify.viewmodel.GroupViewModel


@Composable
fun GroupPage(groupViewModel: GroupViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 50.dp, top = 80.dp, end = 50.dp)
    ) {
        items(groupViewModel.groups.size){ index ->
            Button(
                colors = ButtonDefaults.buttonColors(Color(groupViewModel.groups[index].color)),
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 5.dp),
                onClick = { /*TODO*/ }) {
                Text(text = groupViewModel.groups[index].name,
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupPagePreview() {
    GroupPage(GroupViewModel())
}
