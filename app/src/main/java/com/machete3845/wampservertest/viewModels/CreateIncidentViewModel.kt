package com.machete3845.wampservertest.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machete3845.wampservertest.apis.IncidentApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateIncidentViewModel @Inject constructor(
    private var incidentApi: IncidentApi
) : ViewModel() {

    private val _creationResult = MutableSharedFlow<Boolean>()
    val creationResult: SharedFlow<Boolean> = _creationResult

    fun createIncident(title: String, description: String, priority: String) {
        val status = "open"
        viewModelScope.launch {
            try {
                val response = incidentApi.createIncident(
                    java.util.UUID.randomUUID().toString(),
                    title,
                    description,
                    priority,
                    status,
                    java.sql.Date(System.currentTimeMillis())
                )
                if (response.isSuccessful) {
                    _creationResult.emit(true)
                }
            } catch (e: Exception) {
                println("Error creating incident: ${e.message}")
                _creationResult.emit(false)
            }
        }
    }
}