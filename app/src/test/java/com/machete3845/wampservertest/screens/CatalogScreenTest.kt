package com.machete3845.wampservertest.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.machete3845.wampservertest.viewModels.CatalogViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatalogScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCatalogScreenInitialState() {
        val mockViewModel = mockk<CatalogViewModel>(relaxed = true)
        every { mockViewModel.services } returns MutableStateFlow(emptyList())

        composeTestRule.setContent {
            CatalogScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Service Catalog").assertExists()
        composeTestRule.onNodeWithText("Search services").assertExists()
        composeTestRule.onNodeWithContentDescription("Add Service").assertExists()
    }

    @Test
    fun testCatalogScreenWithServices() {
        val mockViewModel = mockk<CatalogViewModel>(relaxed = true)
        val services = listOf(
            Service("1", "Service 1", "Description 1", "SLA 1", "100"),
            Service("2", "Service 2", "Description 2", "SLA 2", "200")
        )
        every { mockViewModel.services } returns MutableStateFlow(services)

        composeTestRule.setContent {
            CatalogScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Service 1").assertExists()
        composeTestRule.onNodeWithText("Service 2").assertExists()
    }

    @Test
    fun testSearchService() {
        val mockViewModel = mockk<CatalogViewModel>(relaxed = true)
        every { mockViewModel.services } returns MutableStateFlow(emptyList())

        composeTestRule.setContent {
            CatalogScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Search services").performTextInput("test")
        verify { mockViewModel.searchServices("test") }
    }

    @Test
    fun testAddServiceDialog() {
        val mockViewModel = mockk<CatalogViewModel>(relaxed = true)
        every { mockViewModel.services } returns MutableStateFlow(emptyList())

        composeTestRule.setContent {
            CatalogScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithContentDescription("Add Service").performClick()
        composeTestRule.onNodeWithText("Add New Service").assertExists()

        composeTestRule.onNodeWithText("Name").performTextInput("New Service")
        composeTestRule.onNodeWithText("Description").performTextInput("New Description")
        composeTestRule.onNodeWithText("Service level").performTextInput("New Service Level")
        composeTestRule.onNodeWithText("Price").performTextInput("300")

        composeTestRule.onNodeWithText("Add").performClick()

        verify { mockViewModel.addService("New Service", "New Description", "New Service Level", "300") }
    }

    @Test
    fun testDeleteServiceDialog() {
        val mockViewModel = mockk<CatalogViewModel>(relaxed = true)
        val service = Service("1", "Service to Delete", "Description", "Service level", "100")
        every { mockViewModel.services } returns MutableStateFlow(listOf(service))

        composeTestRule.setContent {
            CatalogScreen(onNavigateBack = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Service to Delete").performClick()
        composeTestRule.onNodeWithContentDescription("Delete").performClick()

        composeTestRule.onNodeWithText("Delete Service").assertExists()
        composeTestRule.onNodeWithText("Are you sure you want to delete 'Service to Delete'?").assertExists()

        composeTestRule.onNodeWithText("Delete").performClick()

        verify { mockViewModel.removeService(service) }
    }
}