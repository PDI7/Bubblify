package com.example.bubblify.viewmodel

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.bubblify.model.Group

class GroupViewModel : ViewModel() {
    val groups = listOf<Group>(
        Group("", "Erasmus Friends", 0xff7a40e8),
        Group("", "Family Chausson", 0xff008367),
        Group("", "Jotac People", 0xffc7006e),
    )
}