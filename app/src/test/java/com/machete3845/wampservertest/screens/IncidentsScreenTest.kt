package com.machete3845.wampservertest.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.machete3845.wampservertest.data.Incident
import com.machete3845.wampservertest.viewModels.IncidentsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IncidentsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIncidentsScreenInitialState() {
        val mockViewModel = mockk<IncidentsViewModel>(relaxed = true)
        every { mockViewModel.incidents } returns MutableStateFlow(emptyList())

        composeTestRule.setContent {
            IncidentsScreen(
                onNavigateBack = {},
                onCreateNewIncident = {},
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithText("Incidents").assertExists()
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
        composeTestRule.onNodeWithContentDescription("Create New Incident").assertExists()
    }

    @Test
    fun testIncidentsScreenWithIncidents() {
        val mockViewModel = mockk<IncidentsViewModel>(relaxed = true)
        val incidents = listOf(
            Incident("1", "Incident 1", "Description 1", "High", "Open", "2023-06-01"),
            Incident("2", "Incident 2", "Description 2", "Low", "Closed", "2023-06-02")
        )
        every { mockViewModel.incidents } returns MutableStateFlow(incidents)

        composeTestRule.setContent {
            IncidentsScreen(
                onNavigateBack = {},
                onCreateNewIncident = {},
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithText("Incident 1").assertExists()
        composeTestRule.onNodeWithText("Incident 2").assertExists()
        composeTestRule.onNodeWithText("Description 1").assertExists()
        composeTestRule.onNodeWithText("Description 2").assertExists()
    }

    @Test
    fun testIncidentDetail() {
        val mockViewModel = mockk<IncidentsViewModel>(relaxed = true)
        val incident = Incident("1", "Test Incident", "Test Description", "Medium", "In Progress", "2023-06-03")
        every { mockViewModel.incidents } returns MutableStateFlow(listOf(incident))

        composeTestRule.setContent {
            IncidentsScreen(
                onNavigateBack = {},
                onCreateNewIncident = {},
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithText("Test Incident").performClick()

        composeTestRule.onNodeWithText("Test Incident").assertExists()
        composeTestRule.onNodeWithText("Test Description").assertExists()
        composeTestRule.onNodeWithText("Priority: Medium").assertExists()
        composeTestRule.onNodeWithText("Status: In Progress").assertExists()
        composeTestRule.onNodeWithText("Created at:2023-06-03").assertExists()
    }

    @Test
    fun testCreateNewIncident() {
        var createNewIncidentCalled = false
        val mockViewModel = mockk<IncidentsViewModel>(relaxed = true)
        every { mockViewModel.incidents } returns MutableStateFlow(emptyList())

        composeTestRule.setContent {
            IncidentsScreen(
                onNavigateBack = {},
                onCreateNewIncident = { createNewIncidentCalled = true },
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithContentDescription("Create New Incident").performClick()
        assert(createNewIncidentCalled)
    }

    @Test
    fun testNavigateBack() {
        var navigateBackCalled = false
        val mockViewModel = mockk<IncidentsViewModel>(relaxed = true)
        every { mockViewModel.incidents } returns MutableStateFlow(emptyList())

        composeTestRule.setContent {
            IncidentsScreen(
                onNavigateBack = { navigateBackCalled = true },
                onCreateNewIncident = {},
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(navigateBackCalled)
    }
}