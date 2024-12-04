package com.machete3845.wampservertest.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserSession {
    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    fun setUsername(name: String) {
        _username.value = name
    }

    fun clearUsername() {
        _username.value = null
    }
}