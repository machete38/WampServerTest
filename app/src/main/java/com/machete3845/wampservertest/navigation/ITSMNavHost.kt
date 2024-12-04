package com.machete3845.wampservertest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.machete3845.wampservertest.screens.AuthScreen
import com.machete3845.wampservertest.screens.CatalogScreen
import com.machete3845.wampservertest.screens.CreateIncidentScreen
import com.machete3845.wampservertest.screens.HomeScreen
import com.machete3845.wampservertest.screens.IncidentsScreen
import com.machete3845.wampservertest.screens.MessagesScreen


@Composable
fun ITSMNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "auth") {
        composable("auth") {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate("home") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                onNavigateToMessages = { navController.navigate("messages") },
                onNavigateToCatalog = { navController.navigate("catalog") },
                onNavigateToIncidents = { navController.navigate("incidents") }
            )
        }
        composable("messages") {
            MessagesScreen(onNavigateBack = { navController.navigateUp() })
        }
        composable("catalog") {
            CatalogScreen(onNavigateBack = { navController.navigateUp() })
        }
        composable("incidents") {
            IncidentsScreen(
                onNavigateBack = { navController.navigateUp() },
                onCreateNewIncident = { navController.navigate("create_incident") }
            )
        }
        composable("create_incident") {
            CreateIncidentScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}