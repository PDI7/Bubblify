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
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class SetActivityPageKtTest {

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
            navController.navigate("setActivity/$GROUP_1_ID")
        }

    }

    @Test
    fun setActivityPageComponentsTest() {
        composeTestRule
            .onNodeWithTag("setActivityTitle")
            .assertExists()

        Thread.sleep(1000)

        composeTestRule
            .onNodeWithTag("activityButtonWork")
            .assertExists()

        composeTestRule
            .onNodeWithTag("activityButtonFika")
            .assertExists()

        composeTestRule
            .onNodeWithTag("activityButtonSleep")
            .assertExists()

        composeTestRule
            .onNodeWithTag("activityButtonGym")
            .assertExists()
    }

    @Test
    fun setActivityBackButtonTest() {
        composeTestRule
            .onNodeWithTag("setActivityBackButton")
            .assertExists()
            .performClick()

        Assert.assertEquals("bubbleMain/{groupId}", navController.currentDestination?.route)
    }

    companion object {
        // Unit test group
        private const val GROUP_1_ID = "nLCaoyJWKw59XNuf0Frj"
    }

}