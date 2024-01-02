package com.example.bubblify.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bubblify.model.Group
import com.example.bubblify.model.User
import com.example.bubblify.model.UserGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.util.Util
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StorageServiceTest {

    private var firestore = Firebase.firestore
    private lateinit var storageService: StorageService
    private lateinit var accountService: AccountService

    @Before
    fun setUp() = runTest {
        accountService = AccountService(Firebase.auth)
        storageService = StorageService(firestore, accountService)

        Firebase.auth.signInWithEmailAndPassword(EMAIL, PASSWORD).await()
    }

    @Test
    fun createUpdateDeleteGroup() = runTest {
        // Arrange
        val groupName = "Test Group"
        val updatedGroupName = GROUP_1

        // Act: Create a group
        val groupReference = storageService.createGroup(Group(groupName))

        // Assert: Create a group
        var group = storageService.getGroup(groupReference.id)
        assertEquals(groupName, group!!.name)

        // Act: Update the group
        group.name = updatedGroupName
        storageService.updateGroup(groupReference.id, group)

        // Assert: Update the group
        group = storageService.getGroup(groupReference.id)
        assertEquals(updatedGroupName, group!!.name)

        // Act: Delete the group
        storageService.deleteGroup(groupReference.id)
    }

    @Test
    fun getAllGroupsFromCurrentUser() = runTest {
        // Act
        val groups = storageService.getAllGroupsFromCurrentUser()

        // Assert
        assertNotNull(groups)
        assertContains(groups, GROUP_1)
        assertContains(groups, GROUP_2)
        assertContains(groups, GROUP_3)
    }

    @Test
    fun createDeleteUser() = runTest {
        // Arrange
        val randomId = Util.autoId()

        // Act
        storageService.createUser(randomId, User(USERNAME, EMAIL))

        // Assert
        val result = firestore.collection("Users").document(randomId).get().await().toObject<User>()

        assertEquals(EMAIL, result!!.email)
        assertEquals(USERNAME, result.username)

        // Clean up
        storageService.deleteUser(randomId)

    }

    @Test
    fun getUser() = runTest {
        // Act
        val userReference = storageService.getCurrentUser()

        // Assert
        assertNotNull(userReference)
        assertEquals(EMAIL, userReference.data.email)
        assertEquals(USERNAME, userReference.data.username)
        assertEquals(accountService.currentUserId, userReference.reference.id)
    }

    @Test
    fun addRemoveUserGroup() = runTest {
        // Arrange
        val groupName = "Test addRemoveUserGroup"
        val userReference = storageService.getCurrentUser()
        val groupReference = storageService.createGroup(Group(groupName))

        // Act
        storageService.addUserToGroup(userReference.reference, groupReference)

        // Assert
        val groups = storageService.getAllGroupsWithReferenceFromCurrentUser()
        assertTrue(groups.any { it.reference == groupReference })


        // Clean up
        storageService.removeUserFromGroup(userReference.reference, groupReference)
        storageService.deleteGroup(groupReference.id)
    }

    @Test
    fun setActivityForUserInGroup() = runTest {
        // Arrange
        val userReference = storageService.getCurrentUser()
        val activityReference = firestore.collection(StorageService.ACTIVITY_COLLECTION)
            .document("M1HBlxTypU3MVsz4VxKQ")
        val groupReference =
            firestore.collection(StorageService.GROUP_COLLECTION).document("nLCaoyJWKw59XNuf0Frj")

        // Act
        storageService.setActivityForUserInGroup(
            activityReference,
            userReference.reference,
            groupReference
        )

        // Assert
        val userGroup = firestore.collection(StorageService.USER_GROUP_COLLECTION)
            .whereEqualTo(StorageService.USER_ID_FIELD, userReference.reference)
            .whereEqualTo(StorageService.GROUP_ID_FIELD, groupReference)
            .get().await().documents.first().toObject<UserGroup>()
        assertEquals(activityReference, userGroup!!.activityId)

        // Clean up
        storageService.setActivityForUserInGroup(null, userReference.reference, groupReference)
    }

    private fun assertContains(groups: List<Group>, s: String) {
        assertTrue(groups.any { it.name == s })
    }

    companion object {
        // Unit test account
        private const val EMAIL = "unit-test@gmail.com"
        private const val USERNAME = "unit-test"
        private const val PASSWORD = "wH5^34ATr^\$^Y6"

        // Groups
        private const val GROUP_1 = "Test Group 1"
        private const val GROUP_2 = "Test Group 2"
        private const val GROUP_3 = "Test Group 3"
    }
}