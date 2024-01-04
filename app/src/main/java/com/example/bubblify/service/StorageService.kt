package com.example.bubblify.service

import android.util.Log
import com.example.bubblify.model.Activity
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

    @Deprecated("Use getAllGroupsWithReferenceFromCurrentUser instead")
    suspend fun getGroups(): List<Group>? =
        firestore.collection(GROUP_COLLECTION).get().await().toObjects()

    suspend fun getGroup(groupId: String): Group? =
        firestore.collection(GROUP_COLLECTION).document(groupId).get().await().toObject()

    @Deprecated("Use getAllGroupsWithReferenceFromCurrentUser instead")
    suspend fun getAllGroups(): List<Group> {
        return firestore.collection(GROUP_COLLECTION).get().await().toObjects()
    }

    // Get all the groups from the current User
    @Deprecated("Use getAllGroupsWithReferenceFromCurrentUser instead")
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

    suspend fun getAllGroupsWithReferenceFromCurrentUser(): List<Reference<Group>> {
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
                val group = groupReference!!.get().await().toObject<Group>()
                if (group == null) {
                    document.reference.delete().await()
                    return@mapNotNull null
                }
                Reference(
                    groupReference,
                    group
                )
            }
            .toList()
        // Return the list
        return groups
    }

    suspend fun getAllUsersFromGroup(groupReferenceString: String): List<Reference<User>> {
        val groupReference = firestore.collection(GROUP_COLLECTION).document(groupReferenceString)

        val userGroups = firestore.collection(USER_GROUP_COLLECTION)
            .whereEqualTo(GROUP_ID_FIELD, groupReference)
            .whereEqualTo(USER_GROUP_STATE_FIELD, "ACCEPTED").get().await()

        val users = userGroups.documents
            .filter { it.exists() }
            .mapNotNull { document ->
                val userReference = document.getDocumentReference(USER_ID_FIELD)
                val user = userReference!!.get().await().toObject<User>()
                Reference(
                    userReference,
                    user!!
                )
            }
            .toList()

        return users
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

    suspend fun deleteGroup(groupId: String) {
        val groupReference = firestore.collection(GROUP_COLLECTION).document(groupId)
        firestore.collection(USER_GROUP_COLLECTION).whereEqualTo(GROUP_ID_FIELD, groupReference)
            .get()
            .await()
            .documents.forEach {
                it.reference.delete().await()
            }
        groupReference.delete().await()
    }


    //===============================================================//
    //======================== USER METHODS =========================//
    //===============================================================//

    suspend fun getCurrentUser(): Reference<User> {
        val documentReference =
            firestore.collection(USER_COLLECTION).document(auth.currentUserId).get().await()
        return Reference(
            documentReference.reference,
            documentReference.toObject()!!
        )
    }

    suspend fun getUsersExceptInGroup(groupReferenceString: String): List<Reference<User>> {
        val groupReference = firestore.collection(GROUP_COLLECTION).document(groupReferenceString)

        val userGroups = firestore.collection(USER_GROUP_COLLECTION)
            .whereEqualTo(GROUP_ID_FIELD, groupReference)
            .whereIn(USER_GROUP_STATE_FIELD, listOf("ACCEPTED", "INVITED")).get().await()

        val users = firestore.collection(USER_COLLECTION).get().await()

        val usersInGroup = userGroups.documents.mapNotNull { document ->
            document.toObject<UserGroup>()?.userId
        }

        val result = users.documents
            .filter { it.exists() && !usersInGroup.contains(it.reference) }
            .mapNotNull { document ->
                val userReference = document.reference
                val user = userReference.get().await().toObject<User>()
                Reference(
                    userReference,
                    user!!
                )
            }
            .toList()

        return result
    }

    fun filterUsers(
        users: List<Reference<User>>,
        querySearch: String
    ): List<Reference<User>> {
        return users.filter { user ->
            user.data.username.contains(querySearch, ignoreCase = true)
        }
    }

    suspend fun getUsersFromSearch(
        querySearch: String,
        groupReferenceString: String
    ): List<Reference<User>>? {
        val allUsers = getUsersExceptInGroup(groupReferenceString) ?: return null
        return filterUsers(allUsers, querySearch)
    }

    suspend fun createUser(userId: String, user: User) {
        firestore.collection(USER_COLLECTION).document(userId).set(user).await()
    }

    suspend fun deleteUser(userId: String) {
        firestore.collection(USER_COLLECTION).document(userId).delete().await()
    }

    suspend fun doesUsernameExist(username: String): Boolean {
        val users =
            firestore.collection(USER_COLLECTION).whereEqualTo(USER_NAME_FIELD, username).get()
                .await()
        return users.documents.isNotEmpty()
    }

    //===============================================================//
    //======================== UserGroup METHODS ====================//
    //===============================================================//
    suspend fun setActivityForUserInGroup(
        activityReference: DocumentReference?,
        userReference: DocumentReference,
        groupReference: DocumentReference
    ) {
        val userGroup = firestore.collection(USER_GROUP_COLLECTION)
            .whereEqualTo(USER_ID_FIELD, userReference)
            .whereEqualTo(GROUP_ID_FIELD, groupReference)
            .get()
            .await()

        userGroup.documents.first()
            .reference
            .update(ACTIVITY_ID_FIELD, activityReference)
            .await()
    }

    suspend fun filterUsersGroupsFromActivities(
        activityReference: DocumentReference
    ): List<Reference<UserGroup>> {
        return firestore.collection(USER_GROUP_COLLECTION)
            .whereEqualTo(ACTIVITY_ID_FIELD, activityReference)
            .get()
            .await()
            .documents
            .mapNotNull { document ->
                val activity = document.toObject<UserGroup>()
                Reference(
                    document.reference,
                    activity!!
                )
            }
            .toList()
    }

    suspend fun addUserToGroup(
        userReference: DocumentReference,
        groupReferenceString: String
    ): DocumentReference {
        val groupReference = firestore.collection(GROUP_COLLECTION).document(groupReferenceString)

        val userGroup = UserGroup(userReference, groupReference, null, UserGroupState.ACCEPTED)
        return firestore.collection(USER_GROUP_COLLECTION).add(userGroup).await()
    }

    suspend fun removeUserFromGroup(
        userReference: DocumentReference,
        groupReferenceString: String
    ) {
        val groupReference = firestore.collection(GROUP_COLLECTION).document(groupReferenceString)

        firestore.collection(USER_GROUP_COLLECTION)
            .whereEqualTo(USER_ID_FIELD, userReference)
            .whereEqualTo(GROUP_ID_FIELD, groupReference)
            .get()
            .await()
            .documents.forEach {
                it.reference.delete().await()
            }
    }

    //===============================================================//
    //======================== ACTIVITY METHODS =====================//
    //===============================================================//

    @Deprecated("Use getActivitiesInGroup instead")
    suspend fun getActivities(groupId: String): List<Reference<Activity>> {
        // Get the DocumentReference with the groupId
        val groupRef = firestore.document("/Groups/$groupId")
        // Get all the activities from the current group and return the list
        return firestore.collection(ACTIVITY_COLLECTION).whereEqualTo(GROUP_ID_FIELD, groupRef)
            .get().await().documents
            .mapNotNull { document ->
                val activity = document.toObject<Activity>()
                Reference(
                    document.reference,
                    activity!!
                )
            }
            .toList()
    }

    suspend fun getActivitiesInGroup(groupReferenceString: String): List<Reference<Activity>> {
        val groupReference = firestore.collection(GROUP_COLLECTION).document(groupReferenceString)

        return firestore.collection(ACTIVITY_COLLECTION)
            .whereEqualTo(GROUP_ID_FIELD, groupReference)
            .get()
            .await()
            .documents
            .mapNotNull { document ->
                val activity = document.toObject<Activity>()
                Reference(
                    document.reference,
                    activity!!
                )
            }
            .toList()
    }

    suspend fun addActivityToGroup(activity: Activity, groupReferenceString: String): DocumentReference {
        var groupReference = firestore.collection(GROUP_COLLECTION).document(groupReferenceString)

        activity.groupId = groupReference

        val result = firestore.collection(ACTIVITY_COLLECTION).add(activity).await()

        Log.d("result", result.toString())

        return result
    }

    suspend fun removeActivityFromGroup(activityReference: DocumentReference) {
        activityReference.delete().await()
    }


    // ================= Constants ================================ //
    companion object {
        // Fields
        const val USER_ID_FIELD = "userId"
        const val USER_NAME_FIELD = "username"
        const val GROUP_ID_FIELD = "groupId"
        const val USER_GROUP_STATE_FIELD = "state"
        const val ACTIVITY_ID_FIELD = "activityId"

        // Collection/Documents Constants
        const val USER_GROUP_COLLECTION = "UsersGroups"
        const val GROUP_COLLECTION = "Groups"
        const val USER_COLLECTION = "Users"
        const val ACTIVITY_COLLECTION = "Activities"
    }
}