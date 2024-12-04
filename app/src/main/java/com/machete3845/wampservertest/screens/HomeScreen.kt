package com.machete3845.wampservertest.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.machete3845.wampservertest.utils.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMessages: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    onNavigateToIncidents: () -> Unit
) {
    val username by UserSession.username.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Панель управления ITSM") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            username?.let {
                Text(
                    "Добро пожаловать, ${it}!",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }


            HomeButton(
                icon = Icons.Default.Email,
                text = "Messages",
                onClick = onNavigateToMessages
            )

            Spacer(modifier = Modifier.height(16.dp))

            HomeButton(
                icon = Icons.Default.Build,
                text = "Service Catalog",
                onClick = onNavigateToCatalog
            )

            Spacer(modifier = Modifier.height(16.dp))

            HomeButton(
                icon = Icons.Default.Warning,
                text = "Incidents",
                onClick = onNavigateToIncidents
            )
        }
    }
}

@Composable
fun HomeButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null)
            Text(text)
        }
    }
}