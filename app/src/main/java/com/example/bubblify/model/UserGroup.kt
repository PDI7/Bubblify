package com.example.bubblify.model

import com.google.firebase.firestore.DocumentReference

data class UserGroup (
    val userId: DocumentReference,
    val groupId: DocumentReference,
    val activityId: DocumentReference? = null,
    val state: UserGroupState,
)