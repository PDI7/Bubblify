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

    private suspend fun getUsers(): List<User>? =
        firestore.collection(USER_COLLECTION).get().await().toObjects()

    private fun filterUsers(users: List<User>, querySearch: String): List<User> {
        return users.filter { user ->
            user.username.contains(querySearch, ignoreCase = true)
        }
    }

    suspend fun getUsersFromSearch(querySearch: String): List<User>?  {
        val allUsers = getUsers() ?: return null
        return filterUsers(allUsers, querySearch)
    }

    suspend fun createUser(user: User): DocumentReference? {
        return firestore.collection(USER_COLLECTION).add(user).await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val Group_COLLECTION = "Groups"
        private const val USER_COLLECTION = "Users"
        private const val SAVE_Group_TRACE = "saveGroup"
        private const val UPDATE_Group_TRACE = "updateGroup"
    }
}