package com.example.bubblify.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bubblify.model.Group

class SharedHomeBubbleViewModel : ViewModel(){
    var group by mutableStateOf<Group?>(null)
        private set

    var groupId by mutableStateOf<String?>(null)
        private set

    fun addGroup(group: Group, groupId : String) {
        this.group = group
        this.groupId = groupId
    }
}