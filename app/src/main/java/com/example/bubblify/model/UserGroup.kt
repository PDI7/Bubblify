package com.example.bubblify.model

data class UserGroup (
    val userId: String,
    val groupId: String,
    val activityId: String,
    val state: UserGroupState,
)