package com.example.bubblify

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bubblify.view.LoginPage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginTest {

        @get:Rule(order = 1)
        val composeTestRule = createAndroidComposeRule<MainActivity>()

        @get:Rule(order = 0)
        val hiltRule = HiltAndroidRule(this)


        @Before
        fun setup() {
            //hiltRule.inject()
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