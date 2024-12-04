package com.machete3845.wampservertest.viewModels
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machete3845.wampservertest.apis.ServiceApi
import com.machete3845.wampservertest.data.ServiceResponse
import com.machete3845.wampservertest.screens.Service
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val serviceApi: ServiceApi
) : ViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> = _services

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchServices()
    }

    fun fetchServices() {
        viewModelScope.launch {
            try {
                val response = serviceApi.getServices()
                if (response.isSuccessful) {
                    val serviceResponses = response.body() ?: emptyList()
                    _services.value = serviceResponses.map { it.toService() }
                }
            } catch (e: Exception) {
                Log.d("EXCEPTION", "Error fetching services: ${e.message}")
            }
        }
    }

            fun addService(name: String, description: String, sla: String, price: String) {
                viewModelScope.launch {
                    try {
                        val response = serviceApi.addService(java.util.UUID.randomUUID().toString(), name, description, sla, price)
                        if (response.isSuccessful) {
                            fetchServices()
                        }
                    } catch (e: Exception) {

                    }
                }
            }

    fun removeService(service: Service) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = serviceApi.deleteService(service.id)
                if (response.isSuccessful) {
                    _services.value = _services.value.filter { it.id != service.id }
                } else {
                    _error.value = "Failed to remove service: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error removing service: ${e.message}"
                Log.e("CatalogViewModel", "Error removing service", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

            fun searchServices(query: String) {
                if (query.isEmpty()) {
                    fetchServices()
                } else {
                    _services.value = _services.value.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.description.contains(query, ignoreCase = true)
                    }
                }
            }

        }

    fun ServiceResponse.toService(): Service {
        return Service(
            id = this.id,
            name = this.name,
            description = this.description,
            sla = this.line,
            price = this.price
        )
    }