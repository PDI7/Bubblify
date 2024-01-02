package com.example.bubblify.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bubblify.model.Group
import com.example.bubblify.model.User
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

    private val EMAIL = "unit-test@gmail.com"
    private val USERNAME = "unit-test"
    private val PASSWORD = "wH5^34ATr^\$^Y6"


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
        Firebase.auth.signInWithEmailAndPassword(EMAIL, PASSWORD).await()

        val groupName = "Test Group"
        val updatedGroupName = "Test Group 2"

        // Create a group
        val groupReference = storageService.createGroup(Group(groupName))

        var group = storageService.getGroup(groupReference.id)
        assertEquals(groupName, group!!.name)

        // Update the group
        group.name = updatedGroupName
        storageService.updateGroup(groupReference.id, group)

        group = storageService.getGroup(groupReference.id)
        assertEquals(updatedGroupName, group!!.name)

        // Delete the group
        storageService.deleteGroup(groupReference.id)
    }

    @Test
    fun getAllGroupsFromCurrentUser() = runTest {
        Firebase.auth.signInWithEmailAndPassword(EMAIL, PASSWORD).await()

        val groups = storageService.getAllGroupsFromCurrentUser()
        assertNotNull(groups)

        assertContains(groups, "Test Group 1")
        assertContains(groups, "Test Group 2")
        assertContains(groups, "Test Group 3")

    }

    @Test
    fun createDeleteUser() = runTest {
        Firebase.auth.signInWithEmailAndPassword(EMAIL, PASSWORD).await()

        val randomId = Util.autoId()
        storageService.createUser(randomId, User(USERNAME, EMAIL))

        val result = firestore.collection("Users").document(randomId).get().await().toObject<User>()

        assertEquals(EMAIL, result!!.email)
        assertEquals(USERNAME, result.username)

        storageService.deleteUser(randomId)

    }

    @Test
    fun getUser() = runTest {
        val userReference = storageService.getCurrentUser()

        assertNotNull(userReference)
        assertEquals(EMAIL, userReference.data.email)
        assertEquals(USERNAME, userReference.data.username)
        assertEquals(accountService.currentUserId, userReference.id)
    }

    private fun assertContains(groups: List<Group>, s: String) {
        assertTrue(groups.any { it.name == s })
    }
}