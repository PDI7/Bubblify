package com.example.bubblify.view.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(text: String, modifier: Modifier, action: () -> Unit) {
    OutlinedButton(
        onClick = action,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.87f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.requiredHeight(height = 30.dp)
        )
    }
}

@Composable
fun SecondaryButton(text: String, modifier: Modifier, action: () -> Unit) {
    OutlinedButton(
        onClick = action,
        border = BorderStroke(2.dp, Color.LightGray),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = Color.Black.copy(alpha = 0.87f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.requiredHeight(height = 30.dp)
        )
    }
}