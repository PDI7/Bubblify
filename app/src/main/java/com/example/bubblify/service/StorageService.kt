package com.example.bubblify.service

import com.example.bubblify.model.Group
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class StorageService
@Inject
constructor(private val firestore: FirebaseFirestore,
            private val auth: AccountService) {

    suspend fun getGroup(groupId: String): Group? =
        firestore.collection(Group_COLLECTION).document(groupId).get().await().toObject()

//    suspend fun save(group: Group): String {
//            val groupWithUserId = group.copy( = auth.currentUserId)
//            firestore.collection(Group_COLLECTION).add(groupWithUserId).await().id
//        }

    suspend fun update(group: Group): Unit {
            firestore.collection(Group_COLLECTION).document(group.id).set(group).await()
        }

    suspend fun delete(groupId: String) {
        firestore.collection(Group_COLLECTION).document(groupId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val Group_COLLECTION = "Groups"
        private const val SAVE_Group_TRACE = "saveGroup"
        private const val UPDATE_Group_TRACE = "updateGroup"
    }
}