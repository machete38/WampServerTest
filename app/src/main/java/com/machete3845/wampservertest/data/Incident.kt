package com.machete3845.wampservertest.data

data class Incident(
    val id: String,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val createdAt: String
)