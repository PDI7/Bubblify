package com.example.bubblify.view.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.basicButton(): Modifier {
    return this
        .fillMaxWidth()
        .requiredHeight(height = 70.dp)
        .padding(16.dp, 8.dp)
}

fun Modifier.fieldModifier(): Modifier {
    return this
        .fillMaxWidth()
        .padding(16.dp, 4.dp)
}