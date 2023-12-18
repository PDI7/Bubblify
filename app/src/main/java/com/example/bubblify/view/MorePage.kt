package com.example.bubblify.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bubblify.viewmodel.LoginViewModel
import com.example.bubblify.viewmodel.MoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorePage(moreViewModel: MoreViewModel) {

    Box( //this is the screen
        Modifier
            .requiredWidth(width = 360.dp)
            .requiredHeight(height = 640.dp)
            .background(color = Color.White)
    ) {
        CenterAlignedTopAppBar(
            title = { Text("More", maxLines = 1) },
            navigationIcon = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "get back"
                    )
                }
            }
        )

        var checked by remember { mutableStateOf(true) }

        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
            },
            modifier = Modifier
                .offset(x = 21.dp, y = 102.dp)
        )


        //settings
        Text(
            text = "Dark mode",
            color = Color.Black.copy(alpha = 0.87f),
            style = TextStyle(
                fontSize = 15.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp,
                    y = 80.dp))


        NavBarUI()
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun MorePagePreview() {
    MorePage(moreViewModel = MoreViewModel())
}