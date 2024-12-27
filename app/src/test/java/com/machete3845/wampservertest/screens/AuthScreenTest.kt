package com.machete3845.wampservertest.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.machete3845.wampservertest.viewModels.AuthState
import com.machete3845.wampservertest.viewModels.AuthViewModel
import com.machete3845.wampservertest.viewModels.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAuthScreenInitialState() {
        val mockViewModel = mockk<AuthViewModel>(relaxed = true)
        every { mockViewModel.authState } returns MutableStateFlow(AuthState.Idle)

        composeTestRule.setContent {
            AuthScreen(viewModel = mockViewModel, onAuthSuccess = {})
        }

        composeTestRule.onNodeWithText("Username").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @Test
    fun testLoginButtonClick() {
        val mockViewModel = mockk<AuthViewModel>(relaxed = true)
        every { mockViewModel.authState } returns MutableStateFlow(AuthState.Idle)

        composeTestRule.setContent {
            AuthScreen(viewModel = mockViewModel, onAuthSuccess = {})
        }

        composeTestRule.onNodeWithText("Username").performTextInput("testuser")
        composeTestRule.onNodeWithText("Password").performTextInput("testpassword")
        composeTestRule.onNodeWithText("Login").performClick()

        verify { mockViewModel.login("testuser", "testpassword") }
    }

    @Test
    fun testAuthStateLoading() {
        val mockViewModel = mockk<AuthViewModel>(relaxed = true)
        every { mockViewModel.authState } returns MutableStateFlow(AuthState.Loading)

        composeTestRule.setContent {
            AuthScreen(viewModel = mockViewModel, onAuthSuccess = {})
        }

        composeTestRule.onNode(hasTestTag("progressIndicator")).assertExists()
    }

    @Test
    fun testAuthStateError() {
        val mockViewModel = mockk<AuthViewModel>(relaxed = true)
        every { mockViewModel.authState } returns MutableStateFlow(AuthState.Error("Login failed"))

        composeTestRule.setContent {
            AuthScreen(viewModel = mockViewModel, onAuthSuccess = {})
        }

        composeTestRule.onNodeWithText("Login failed").assertExists()
    }

    @Test
    fun testAuthStateSuccess() {
        val mockViewModel = mockk<AuthViewModel>(relaxed = true)
        every { mockViewModel.authState } returns MutableStateFlow(AuthState.Success(User("testuser", "Test User", 0)))

        var authSuccessCalled = false
        composeTestRule.setContent {
            AuthScreen(
                viewModel = mockViewModel,
                onAuthSuccess = { authSuccessCalled = true }
            )
        }

        composeTestRule.waitForIdle()
        assert(authSuccessCalled)
    }
}