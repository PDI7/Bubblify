package com.example.bubblify.view.common

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.MainActivity
import com.example.bubblify.service.AccountService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class NavigationComposableKtTest {

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

            NavHost(navController = navController, startDestination = "home") {
                composable("home") { /* nothing */ }
                composable("profile") { /* nothing */ }
                composable("more") { /* nothing */ }
            }
            NavigationBar(navController)
        }

    }

    @Test
    fun navigationBarComponentsTest() {
        composeTestRule
            .onNodeWithTag("homeButton")
            .assertExists()

        composeTestRule
            .onNodeWithTag("moreButton")
            .assertExists()

        composeTestRule
            .onNodeWithTag("profileButton")
            .assertExists()
    }

    @Test
    fun clickHomeButtonTest() {
        composeTestRule
            .onNodeWithTag("homeButton")
            .performClick()

        assertEquals("home", navController.currentDestination?.route)
    }

    @Test
    fun clickMoreButtonTest() {
        composeTestRule
            .onNodeWithTag("moreButton")
            .performClick()

        assertEquals("more", navController.currentDestination?.route)
    }

    @Test
    fun clickProfileButtonTest() {
        composeTestRule
            .onNodeWithTag("profileButton")
            .performClick()

        assertEquals("profile", navController.currentDestination?.route)
    }

}