package com.example.bubblify.model

import com.google.firebase.firestore.DocumentReference

data class UserGroup (
    val userId: DocumentReference? = null,
    val groupId: DocumentReference? = null,
    val activityId: DocumentReference? = null,
    val state: UserGroupState = UserGroupState.INVITED
)