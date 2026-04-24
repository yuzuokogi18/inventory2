package com.example.inventori2.features.notification.domain.entities

data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean
)
