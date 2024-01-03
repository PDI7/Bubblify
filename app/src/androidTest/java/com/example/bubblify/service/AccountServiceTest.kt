package com.example.bubblify.service

import com.example.bubblify.model.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class AccountServiceTest {

    private lateinit var accountService: AccountService

    @Before
    fun setUp() {
        accountService = AccountService(Firebase.auth)
    }

    @Test
    fun loginUser() = runTest {
        // Act
        accountService.loginUser(EMAIL, PASSWORD).collectLatest { result ->
            when (result) {
                is Resource.Loading -> { /* Do nothing */
                }

                is Resource.Success -> {
                    // Assert
                    assertTrue(accountService.hasUser)
                    assertEquals(accountService.currentUserId, UUID)
                }

                is Resource.Error -> {
                    fail("Login failed: " + result.message)
                }
            }
        }
    }

    @Test
    fun loginWithBadCredentialsUser() = runTest {
        // Arrange
        // Make sure there is no user logged in
        accountService.signOut()

        // Act
        accountService.loginUser(EMAIL, "asdf").collectLatest { result ->
            when (result) {
                is Resource.Loading -> { /* Do nothing */
                }

                is Resource.Success -> {
                    fail("Login should not be successful for bad credentials")
                }

                is Resource.Error -> {
                    assertFalse(accountService.hasUser)
                    assertEquals("", accountService.currentUserId)
                }
            }
        }
    }

    @Test
    fun registerDeleteUser() = runTest {
        val tempMail = Random.nextInt(0, 100).toString() + EMAIL

        // Act: register user
        accountService.registerUser(tempMail, PASSWORD).collectLatest { result ->
            when (result) {
                is Resource.Loading -> { /* Do nothing */
                }

                is Resource.Success -> {
                    // Assert: register user
                    Firebase.auth.signInWithEmailAndPassword(tempMail, PASSWORD).await()
                    assertTrue(accountService.hasUser)
                    assertNotEquals("", accountService.currentUserId)

                    // Act: delete user
                    accountService.deleteAccount()

                    // Assert: delete user
                    assertFalse(accountService.hasUser)
                    assertEquals("", accountService.currentUserId)
                }

                is Resource.Error -> {
                    fail("Register failed: " + result.message)
                }
            }
        }
    }

    @Test
    fun registerAlreadyExistingUser() = runTest {
        // Arrange
        // Make sure there is no user logged in
        accountService.signOut()

        // Act: register user
        accountService.registerUser(EMAIL, PASSWORD).collectLatest { result ->
            when (result) {
                is Resource.Loading -> { /* Do nothing */
                }

                is Resource.Success -> {
                    fail("Register should not be successful for already existing user")
                }

                is Resource.Error -> {
                    println(result.message)
                    println(accountService.hasUser)
                    // Assert: register user
                    assertFalse(accountService.hasUser)
                }
            }
        }
    }

    @Test
    fun signOut() = runTest {
        // Arrange
        Firebase.auth.signInWithEmailAndPassword(EMAIL, PASSWORD).await()

        // Act
        assertTrue(accountService.hasUser)
        assertEquals(UUID, accountService.currentUserId)
        accountService.signOut()

        // Assert
        assertFalse(accountService.hasUser)
        assertEquals("", accountService.currentUserId)

    }

    companion object {
        // Unit test account
        private const val UUID = "IeE078xdTcgwLAUkOQKTSVd2hjD2"
        private const val EMAIL = "unit-test@gmail.com"
        private const val PASSWORD = "wH5^34ATr^\$^Y6"
    }
}