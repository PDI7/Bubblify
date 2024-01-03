package com.example.bubblify.viewmodel.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.bubblify.model.Group

class GroupState {
    var group by mutableStateOf<Group?>(null)
        private set

    var groupId by mutableStateOf<String?>(null)
        private set

    fun addGroup(group: Group, groupId : String) {
        this.group = group
        this.groupId = groupId
    }
}