package com.example.bubblify.view

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.bubblify.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginTest {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        hiltRule.inject()

        // navigate to login page
        val appContext = InstrumentationRegistry.getInstrumentation()
        composeTestRule.activity.setContent {
//                navController = rememberNavController()
//                NavGraph(navController)
        }
    }


    @Test
    fun loginButtonTest() {

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

        Thread.sleep(10000)

    }

    companion object {
        // Unit test account
        private const val EMAIL = "unit-test@gmail.com"
        private const val PASSWORD = "wH5^34ATr^\$^Y6"
    }

}