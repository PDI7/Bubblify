package com.example.bubblify.view

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.MainActivity
import com.example.bubblify.MainState
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


        @Before
        fun setup() {
            composeTestRule.activity.setContent {
                LoginPage(MainState(rememberNavController()))
            }
        }


        @Test
        fun loginButtonTest() {

            // Perform actions
            composeTestRule
                .onNodeWithTag("loginButton")
                .performClick()


        }

}