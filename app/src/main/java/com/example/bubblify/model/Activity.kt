package com.example.bubblify.model

data class Activity(
    val name: String,
    val groupId: String, // TODO CHANGE TO DocumentReference
    val icon: ActivityIcon
)