package com.example.bubblify.view

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.BubblifyApp
import com.example.bubblify.MainActivity
import com.example.bubblify.service.AccountService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HomePageKtTest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var accountService: AccountService

    private lateinit var navController: NavHostController

    @Before
    fun setup() = runTest {
        hiltRule.inject()
        accountService.signOut()

        composeTestRule.activity.setContent {
            navController = rememberNavController()
            BubblifyApp(navController)

            // Navigate to sign up page
            navController.navigate("home")
        }

        Firebase.auth.signInWithEmailAndPassword(EMAIL, PASSWORD).await()
    }

    @Test
    fun homePageComponentsTest() {
        composeTestRule
            .onNodeWithTag("groupsTitle")
            .assertExists()

        Thread.sleep(1500)

        composeTestRule
            .onNodeWithTag("groupButtonTest Group 1")
            .assertExists()

        composeTestRule
            .onNodeWithTag("addGroupButton")
            .assertExists()
    }

    @Test
    fun clickOnGroupTest() {
        composeTestRule
            .onNodeWithTag("groupsTitle")
            .assertExists()

        Thread.sleep(1500)

        // Perform actions
        composeTestRule
            .onNodeWithTag("groupButtonTest Group 1")
            .performClick()

        Assert.assertEquals("bubbleMain/{groupId}", navController.currentDestination?.route)
    }

    companion object {
        // Unit test account
        private const val EMAIL = "unit-test@gmail.com"
        private const val PASSWORD = "wH5^34ATr^\$^Y6"
    }
}