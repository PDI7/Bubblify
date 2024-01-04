package com.example.bubblify.view

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.BubblifyApp
import com.example.bubblify.MainActivity
import com.example.bubblify.service.AccountService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LoginPageKtTest {

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
        }
    }

    @Test
    fun loginPageComponentsTest() {
        composeTestRule
            .onNodeWithTag("emailField")
            .assertExists()

        composeTestRule
            .onNodeWithTag("passwordField")
            .assertExists()

        composeTestRule
            .onNodeWithTag("loginButton")
            .assertExists()

        composeTestRule
            .onNodeWithTag("signUpButton")
            .assertExists()
    }

    @Test
    fun clickSignUpTest() {
        // Perform actions
        composeTestRule
            .onNodeWithTag("signUpButton")
            .performClick()

        assertEquals("signUp", navController.currentDestination?.route)
    }


    @Test
    fun loginTest() {
        // Perform actions
        composeTestRule
            .onNodeWithTag("emailField")
            .performTextInput(EMAIL)

        composeTestRule
            .onNodeWithTag("passwordField")
            .performTextInput(PASSWORD)

        composeTestRule
            .onNodeWithTag("loginButton")
            .performClick()

        Thread.sleep(1000)
        assertEquals("home", navController.currentDestination?.route)
    }

    @Test
    fun loginValidationTest() {
        // Perform actions
        composeTestRule
            .onNodeWithTag("loginButton")
            .performClick()

        Thread.sleep(1000)
        assertEquals("login", navController.currentDestination?.route)
    }

    companion object {
        // Unit test account
        private const val EMAIL = "unit-test@gmail.com"
        private const val PASSWORD = "wH5^34ATr^\$^Y6"
    }

}