package com.machete3845.wampservertest.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machete3845.wampservertest.apis.IncidentApi
import com.machete3845.wampservertest.data.Incident
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IncidentsViewModel @Inject constructor(
    private val incidentApi: IncidentApi
) : ViewModel() {

    private val _incidents = MutableStateFlow<List<Incident>>(emptyList())
    val incidents: StateFlow<List<Incident>> = _incidents

    init {
        fetchIncidents()
    }

    private fun fetchIncidents() {
        viewModelScope.launch {
            try {
                val response = incidentApi.getIncidents()
                if (response.isSuccessful) {
                    _incidents.value = response.body() ?: emptyList()
                }
                else
                {
                    Log.d("EXCEPTION", "Error fetching incidents")
                }
            } catch (e: Exception) {
                Log.d("EXCEPTION", "Error fetching incidents: ${e.message}")
            }
        }
    }


}