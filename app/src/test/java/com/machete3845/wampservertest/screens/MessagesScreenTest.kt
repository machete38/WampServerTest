package com.machete3845.wampservertest.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.machete3845.wampservertest.data.Message
import com.machete3845.wampservertest.viewModels.MessagesViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MessagesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMessagesScreenInitialState() {
        val mockViewModel = mockk<MessagesViewModel>(relaxed = true)
        every { mockViewModel.messages } returns MutableStateFlow(listOf())
        every { mockViewModel.username } returns MutableStateFlow("TestUser")

        composeTestRule.setContent {
            MessagesScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Messages").assertExists()
        composeTestRule.onNodeWithText("Type a message").assertExists()
        composeTestRule.onNodeWithContentDescription("Send").assertExists()
    }

    @Test
    fun testMessagesScreenWithMessages() {
        val mockViewModel = mockk<MessagesViewModel>(relaxed = true)
        val messages = listOf(
            Message(username = "User1", msg = "Hello", 0),
            Message(username = "User2", msg = "Hi there", 1)
        )
        every { mockViewModel.messages } returns MutableStateFlow(messages)
        every { mockViewModel.username } returns MutableStateFlow("TestUser")

        composeTestRule.setContent {
            MessagesScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("User1").assertExists()
        composeTestRule.onNodeWithText("Hello").assertExists()
        composeTestRule.onNodeWithText("User2").assertExists()
        composeTestRule.onNodeWithText("Hi there").assertExists()
    }

    @Test
    fun testSendMessage() {
        val mockViewModel = mockk<MessagesViewModel>(relaxed = true)
        every { mockViewModel.messages } returns MutableStateFlow(listOf())
        every { mockViewModel.username } returns MutableStateFlow("TestUser")

        composeTestRule.setContent {
            MessagesScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Type a message").performTextInput("New message")
        composeTestRule.onNodeWithContentDescription("Send").performClick()

        verify { mockViewModel.sendMessage("New message") }
    }

    @Test
    fun testMessageDetail() {
        val mockViewModel = mockk<MessagesViewModel>(relaxed = true)
        val message = Message(username = "User1", msg = "Hello, this is a long message for testing",  1)
        every { mockViewModel.messages } returns MutableStateFlow(listOf(message))
        every { mockViewModel.username } returns MutableStateFlow("TestUser")

        composeTestRule.setContent {
            MessagesScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("User1").performClick()
        composeTestRule.onNodeWithText("Hello, this is a long message for testing").assertExists()
    }

    @Test
    fun testUnauthenticatedState() {
        val mockViewModel = mockk<MessagesViewModel>(relaxed = true)
        every { mockViewModel.messages } returns MutableStateFlow(listOf())
        every { mockViewModel.username } returns MutableStateFlow(null)

        composeTestRule.setContent {
            MessagesScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Please log in to view messages").assertExists()
    }
}