package com.example.bubblify.model

import com.google.firebase.firestore.DocumentReference

data class Activity(
    val groupId: DocumentReference? = null,
    val name: String = "",
    val icon: ActivityIcon? = null
)