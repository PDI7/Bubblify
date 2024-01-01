package com.example.bubblify.service

import com.example.bubblify.model.Group
import com.example.bubblify.model.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageService
@Inject
constructor(private val firestore: FirebaseFirestore,
            private val auth: AccountService) {

    suspend fun getGroups(): List<Group>? =
        firestore.collection(GROUP_COLLECTION).get().await().toObjects()

    // Get all the groups from the current User
    suspend fun  getAllGroupsFromCurrentUser(): List<Group> {
        // Get the current user ID
        val userReference = auth.currentUserId
        // Fetch all the groups where the user are
        val userGroups = firestore.collection(USER_GROUP_COLLECTION).whereEqualTo(USER_ID_FIELD, userReference).get().await()
        // Fetch all the groups where the reference (groupId) corresponding
        val groups = userGroups.documents
            .filter { it.exists() }
            .mapNotNull { document ->
                val groupReference = document.getDocumentReference(GROUP_ID_FIELD)
                groupReference!!.get().await().toObject(Group::class.java)
            }
            .toList()
        // Return the list
        return groups
    }

    suspend fun createGroup(group: Group): DocumentReference? =
        firestore.collection(GROUP_COLLECTION).add(group).await()


    suspend fun getGroup(groupId: String): Group? =
        firestore.collection(GROUP_COLLECTION).document(groupId).get().await().toObject()

//    suspend fun save(group: Group): String {
//            val groupWithUserId = group.copy( = auth.currentUserId)
//            firestore.collection(Group_COLLECTION).add(groupWithUserId).await().id
//        }

    /*suspend fun update(group: Group): Unit {
            firestore.collection(Group_COLLECTION).document(group.id).set(group).await()
        }*/

    suspend fun delete(groupId: String) {
        firestore.collection(GROUP_COLLECTION).document(groupId).delete().await()
    }

    suspend fun createUser(user: User): DocumentReference? {
        return firestore.collection(USER_COLLECTION).add(user).await()
    }

    companion object {
        //
        private const val USER_ID_FIELD = "userId"
        private const val GROUP_ID_FIELD = "groupId"
        // Collection/Documents Constants
        private const val USER_GROUP_COLLECTION = "UsersGroups"
        private const val GROUP_COLLECTION = "Groups"
        private const val USER_COLLECTION = "Users"
        // Traces
        private const val SAVE_Group_TRACE = "saveGroup"
        private const val UPDATE_Group_TRACE = "updateGroup"
    }
}