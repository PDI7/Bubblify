package com.example.bubblify.service

import android.util.Log
import android.content.ContentValues.TAG
import com.example.bubblify.model.Group
import com.example.bubblify.model.User
import com.google.firebase.firestore.DocumentReference
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class StorageService
@Inject
constructor(private val firestore: FirebaseFirestore,
            private val auth: AccountService) {

    suspend fun getGroup(groupId: String): Group? =
        firestore.collection(GROUP_COLLECTION).document(groupId).get().await().toObject()

//    suspend fun save(group: Group): String {
//            val groupWithUserId = group.copy( = auth.currentUserId)
//            firestore.collection(Group_COLLECTION).add(groupWithUserId).await().id
//        }

    //===============================================================//
    //======================== GROUP METHODS ========================//
    //===============================================================//

    // Get all groups from the database
    suspend fun getAllGroups(): List<Group> {
        return firestore.collection(GROUP_COLLECTION).get().await().toObjects()
    }

    suspend fun update(group: Group): Unit {
        firestore.collection(GROUP_COLLECTION).document(group.id).set(group).await()
    }

    suspend fun delete(groupId: String) {
        firestore.collection(GROUP_COLLECTION).document(groupId).delete().await()
    }

    //===============================================================//
    //======================== USER METHODS =========================//
    //===============================================================//
    suspend fun createUser(user: User): DocumentReference? {
        return firestore.collection(USER_COLLECTION).add(user).await()
    }


    // ================= Collections ID's ===================== //
    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val GROUP_COLLECTION = "Groups"
        private const val USER_COLLECTION = "Users"
        private const val SAVE_Group_TRACE = "saveGroup"
        private const val UPDATE_Group_TRACE = "updateGroup"
    }
}