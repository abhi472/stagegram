package com.abhi.stageapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhi.stageapp.data.model.Account
import com.abhi.stageapp.screens.Greeting
import com.abhi.stageapp.states.ApiState
import com.abhi.stageapp.ui.theme.StageAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun mainScreenTest() {
        composeTestRule.setContent {
            Greeting(
                ApiState(
                    account = listOf(
                        Account(
                            "",
                            "",
                            "",
                            listOf(
                                "https://images.pexels.com/photos/26690028/pexels-photo-26690028.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
                                "a"
                            )

                        )
                    )
                ),

            )

        }

        // lazy row items displayed
        composeTestRule.onNodeWithTag("profile").assertIsDisplayed()
        //lazy row item clicked to display dialog
        composeTestRule.onNodeWithTag("profile").performClick()
        //asserting dialog displayed
        composeTestRule.onNodeWithTag("stories").assertIsDisplayed()
        //asserting dialog item displayed
        composeTestRule.onNodeWithTag("progress 0").assertIsDisplayed()
        //dialog box clicked
        composeTestRule.onNodeWithTag("story").performClick()
        // Since there are two items, dialog should still show
        composeTestRule.onNodeWithTag("stories").assertIsDisplayed()
        //dialog box clicked again, dialog should close now
        composeTestRule.onNodeWithTag("story").performClick()
        //asserting dialog not displayed
        composeTestRule.onNodeWithTag("stories").assertIsNotDisplayed()


    }
}