package com.machete3845.wampservertest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.machete3845.wampservertest.data.Incident
import com.machete3845.wampservertest.viewModels.IncidentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentsScreen(
    onNavigateBack: () -> Unit,
    onCreateNewIncident: () -> Unit,
    viewModel: IncidentsViewModel = hiltViewModel()
) {
    val incidents by viewModel.incidents.collectAsState(initial = emptyList())
    var selectedIncident by remember { mutableStateOf<Incident?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incidents") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onCreateNewIncident) {
                        Icon(Icons.Filled.Add, contentDescription = "Create New Incident")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (selectedIncident == null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(incidents) { incident ->
                    IncidentItem(
                        incident = incident,
                        onClick = { selectedIncident = incident }
                    )
                }
            }
        } else {
            IncidentDetail(
                incident = selectedIncident!!,
                onBack = { selectedIncident = null }
            )
        }
    }
}
@Composable
fun IncidentItem(
    incident: Incident,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(getPriorityColor(incident.priority), shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = incident.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = incident.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Created at: "+incident.createdAt,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun IncidentDetail(
    incident: Incident,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = incident.title,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(getPriorityColor(incident.priority), shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Priority: ${incident.priority}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = incident.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Status: ${incident.status}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Created at:"+incident.createdAt,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

fun getPriorityColor(priority: String): Color {
    return when (priority.toLowerCase()) {
        "high" -> Color.Red
        "medium" -> Color.Yellow
        "low" -> Color.Green
        else -> Color.Gray
    }
}

