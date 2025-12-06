package com.example.moviebrowser

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppEndToEndTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAppEndToEnd() {
        // 1. Type "Matrix" in the search field.
        composeTestRule.onNodeWithText("Search").performTextInput("Matrix")

        // 2. Wait for the movie list to appear and click on the first movie.
        composeTestRule.waitUntil(3000) {
            composeTestRule
                .onAllNodesWithText("The Matrix", substring = true)
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText("The Matrix", substring = true).performClick()

        // 3. Assert that the detail screen is displayed and shows the correct movie title.
        composeTestRule.onNodeWithText("Title: The Matrix", substring = true).assertIsDisplayed()
    }
}
