package com.machete3845.wampservertest.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machete3845.wampservertest.apis.MessagesApi
import com.machete3845.wampservertest.data.Message
import com.machete3845.wampservertest.utils.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val messageApi: MessagesApi,
    private val userSession: UserSession
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    val username: StateFlow<String?> = userSession.username

    init {
        fetchMessages()
    }

    fun fetchMessages() {
        viewModelScope.launch {
            try {
                val response = messageApi.getMessages()
                if (response.isSuccessful) {
                    _messages.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {

            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                val currentUsername = username.first()
                if (currentUsername != null) {
                    val response = messageApi.sendMessage(currentUsername, message)
                    if (response.isSuccessful) {
                        fetchMessages()
                    }
                }
            } catch (e: Exception) {

            }
        }
    }
}