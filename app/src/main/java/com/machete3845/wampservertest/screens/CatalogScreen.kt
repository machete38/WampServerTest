package com.machete3845.wampservertest.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.machete3845.wampservertest.ServiceDetail
import com.machete3845.wampservertest.ServiceItem
import com.machete3845.wampservertest.viewModels.CatalogViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onNavigateBack: () -> Unit,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val services by viewModel.services.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var serviceToDelete by remember { mutableStateOf<Service?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Service Catalog") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Service")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (selectedService == null) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.searchServices(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search services") },
                    trailingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                )
                LazyColumn {
                    items(
                        items = services,
                        key = { it.id }
                    ) { service ->
                        ServiceItem(
                            service = service,
                            onClick = { selectedService = service },
                            onDelete = { serviceToDelete = service }
                        )
                    }
                }
            } else {
                ServiceDetail(
                    service = selectedService!!,
                    onBack = { selectedService = null }
                )
            }
        }
    }

    if (showAddDialog) {
        AddServiceDialog(
            onDismiss = { showAddDialog = false },
            onServiceAdded = { name, description, sla, price ->
                viewModel.addService(name, description, sla, price)
                showAddDialog = false
            }
        )
    }

    serviceToDelete?.let { service ->
        DeleteServiceDialog(
            service = service,
            onConfirm = {
                viewModel.removeService(service)
                serviceToDelete = null
            },
            onDismiss = { serviceToDelete = null }
        )
    }
}

@Composable
fun DeleteServiceDialog(
    service: Service,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Service") },
        text = { Text("Are you sure you want to delete '${service.name}'?") },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddServiceDialog(
    onDismiss: () -> Unit,
    onServiceAdded: (String, String, String, String) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var sla by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Service") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                TextField(
                    value = sla,
                    onValueChange = { sla = it },
                    label = { Text("SLA") }
                )
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onServiceAdded(name, description, sla, price)
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

data class Service(
    val id: String,
    val name: String,
    val description: String,
    val sla: String,
    val price: String
)
