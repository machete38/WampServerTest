package com.machete3845.wampservertest.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserSession {
    private val _username = MutableStateFlow<String?>(null)
    private val _role = MutableStateFlow<Int?>(null)

    val username: StateFlow<String?> = _username
    val role: StateFlow<Int?> = _role

    fun setUsername(name: String) {
        _username.value = name
    }

    fun clearUsername() {
        _username.value = null
    }

    fun setRole(role: Int) {
        _role.value = role
    }

    fun clearRole() {
        _role.value = null
    }
    fun pickRole(it: Int): String {
        return when (it){
            0 -> "Пользователь"
            1 -> "Администратор"
            2 -> "Сотрудник поддержки"
            else -> "Неизвестная роль"
        }
    }
}