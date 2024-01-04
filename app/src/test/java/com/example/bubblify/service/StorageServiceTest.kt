package com.example.bubblify.service

import com.example.bubblify.model.Reference
import com.example.bubblify.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StorageServiceTest {

    @Mock
    private lateinit var firestore: FirebaseFirestore

    @Mock
    private lateinit var auth: AccountService

    @Mock
    private lateinit var colRef: CollectionReference

    @Mock
    private lateinit var mockTest: Task<QuerySnapshot>

    private lateinit var storageService: StorageService

    @Before
    fun setUp() {
        storageService = StorageService(firestore, auth)
    }

    @Test
    fun filterUsers(){
            Assert.assertNotNull(firestore)
            Assert.assertNotNull(storageService)

            val mockUser1 = Reference(
                Mockito.mock(DocumentReference::class.java),
                User("Pdi7", "emailTest")
            )

            val mockUser2 = Reference(
                Mockito.mock(DocumentReference::class.java),
                User("Username", "emailTest")
            )

            val mockUser3 = Reference(
                Mockito.mock(DocumentReference::class.java),
                User("Cessouille", "emailTest")
            )

            val users = listOf( mockUser1, mockUser2, mockUser3 )

            val expected = listOf( mockUser2, mockUser3 )

            val result = storageService.filterUsers(users, "e")

            Assert.assertEquals(2, result.size)
            Assert.assertEquals(expected, result)
    }
}