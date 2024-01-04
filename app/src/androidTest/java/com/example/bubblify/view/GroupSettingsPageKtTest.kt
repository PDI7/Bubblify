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
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class GroupSettingsPageKtTest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var accountService: AccountService

    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        accountService.signOut()

        composeTestRule.activity.setContent {
            navController = rememberNavController()
            BubblifyApp(navController)

            // Navigate to sign up page
            navController.navigate("groupSettings/${GROUPID}")
        }


    }

    @Test
    fun GroupSettingsPageComponentsTest() {
        composeTestRule
            .onNodeWithTag("backArrow")
            .assertExists()

        composeTestRule
            .onNodeWithTag("activitiesTab")
            .assertExists()

        composeTestRule
            .onNodeWithTag("membersTab")
            .assertExists()

        composeTestRule
            .onNodeWithTag("addMembersButton")
            .assertExists()
    }

    @Test
    fun clickBackTest() {
        // Perform actions
        composeTestRule
            .onNodeWithTag("backArrow")
            .performClick()

        Assert.assertEquals("bubbleMain/{groupId}", navController.currentDestination?.route)
    }

    @Test
    fun clickAddMembersTest() {
        // Perform actions
        composeTestRule
            .onNodeWithTag("addMembersButton")
            .performClick()

        Assert.assertEquals("addMembers/{groupId}", navController.currentDestination?.route)
    }

    @Test
    fun clickAddActivitiesTest() {
        // Perform actions
        composeTestRule
            .onNodeWithTag("activitiesTab")
            .performClick()

        composeTestRule
            .onNodeWithTag("addActivitiesButton")
            .performClick()

        Assert.assertEquals("addActivities/{groupId}", navController.currentDestination?.route)
    }

    companion object {
        private const val GROUPID = "nLCaoyJWKw59XNuf0Frj"
    }
}