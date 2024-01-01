package com.example.bubblify

import com.example.bubblify.service.AccountService
import com.example.bubblify.service.StorageService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {


    // @Celian this is some example code for how to mock
    // If you need to test a suspend function, you need to use runTest -> Tell me and I'll help you...

    @Mock
    private lateinit var firestore: FirebaseFirestore

    @Mock
    private lateinit var auth: AccountService

    @Mock
    private lateinit var colRef: CollectionReference

    private lateinit var storageService: StorageService

    @Before
    fun setUp() {
        storageService = StorageService(firestore, auth)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getGroupById() {
        assertNotNull(firestore)
        assertNotNull(storageService)


        `when`(firestore.collection(ArgumentMatchers.any(String::class.java))).thenReturn(colRef)

        val result = firestore.collection("test")

        assertEquals(colRef, result)
    }


}