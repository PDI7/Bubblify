package com.example.bubblify.service

import com.example.bubblify.model.Group
import com.example.bubblify.model.Reference
import com.example.bubblify.model.User
import com.example.bubblify.model.UserGroup
import com.example.bubblify.model.UserGroupState
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageService
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) {

    //===============================================================//
    //======================== GROUP METHODS ========================//
    //===============================================================//

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
        firestore.collection(GROUP_COLLECTION).document(groupId).get().await().toObject()

    // Get all groups from the database
    suspend fun getAllGroups(): List<Group> {
        return firestore.collection(GROUP_COLLECTION).get().await().toObjects()
    }

    // Get all the groups from the current User
    suspend fun getAllGroupsFromCurrentUser(): List<Group> {
        // Get the current user ID
        val userReference = firestore.collection(USER_COLLECTION).document(auth.currentUserId)
        // Fetch all the groups where the user are
        val userGroups =
            firestore.collection(USER_GROUP_COLLECTION).whereEqualTo(USER_ID_FIELD, userReference)
                .get().await()
        // Fetch all the groups where the reference (groupId) corresponding
        val groups = userGroups.documents
            .filter { it.exists() }
            .mapNotNull { document ->
                val groupReference = document.getDocumentReference(GROUP_ID_FIELD)
                groupReference!!.get().await().toObject<Group>()
            }
            .toList()
        // Return the list
        return groups
    }

    suspend fun createGroup(group: Group): DocumentReference {
        val userReference = firestore.collection(USER_COLLECTION).document(auth.currentUserId)
        val groupReference = firestore.collection(GROUP_COLLECTION).add(group).await()
        firestore.collection(USER_GROUP_COLLECTION)
            .add(UserGroup(userReference, groupReference, null, UserGroupState.ACCEPTED))
            .await()
        return groupReference
    }

    suspend fun updateGroup(groupId: String, group: Group): Unit {
        firestore.collection(GROUP_COLLECTION).document(groupId).set(group).await()
    }

    /*suspend fun update(group: Group): Unit {
            firestore.collection(Group_COLLECTION).document(group.id).set(group).await()
        }*/
    suspend fun deleteGroup(groupId: String) {
        val groupReference = firestore.collection(GROUP_COLLECTION).document(groupId)
        firestore.collection(USER_GROUP_COLLECTION).whereEqualTo(GROUP_ID_FIELD, groupId).get()
            .await()
            .documents.forEach {
                it.reference.delete().await()
            }
        groupReference.delete().await()
    }

    suspend fun delete(groupId: String) {
        firestore.collection(GROUP_COLLECTION).document(groupId).delete().await()
    //===============================================================//
    //======================== USER METHODS =========================//
    //===============================================================//

    suspend fun getCurrentUser(): Reference<User> {
        val documentReference =
            firestore.collection(USER_COLLECTION).document(auth.currentUserId).get().await()
        return Reference(
            documentReference.id,
            documentReference.toObject()!!
        )
    }

    suspend fun createUser(userId: String, user: User) {
        firestore.collection(USER_COLLECTION).document(userId).set(user).await()
    }


    suspend fun deleteUser(userId: String) {
        firestore.collection(USER_COLLECTION).document(userId).delete().await()
    }


    // ================= Constants ================================ //
    companion object {
        // Fields
        private const val USER_ID_FIELD = "userId"
        private const val GROUP_ID_FIELD = "groupId"
        // Collection/Documents Constants
        private const val USER_GROUP_COLLECTION = "UsersGroups"
        private const val GROUP_COLLECTION = "Groups"
        private const val UUID_FIELD = "uuid"
        private const val GROUP_ID_FIELD = "groupId"
        private const val ACTIVITY_ID_FIELD = "activityId"

        // Collections
        private const val GROUP_COLLECTION = "Groups"
        private const val USER_COLLECTION = "Users"
        // Traces
        private const val SAVE_Group_TRACE = "saveGroup"
        private const val UPDATE_Group_TRACE = "updateGroup"
        private const val USER_GROUP_COLLECTION = "UsersGroups"
        private const val ACTIVITY_COLLECTION = "Activities"
    }
}