package com.example.bubblify.model

data class UserGroup (
    val id: String,
    val user: User,
    val group: Group,
    val activity: Activity,
    val state: UserGroupState,
)