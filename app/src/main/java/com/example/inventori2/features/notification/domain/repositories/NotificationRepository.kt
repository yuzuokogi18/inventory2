package com.example.inventori2.features.notification.domain.repositories

import com.example.inventori2.features.notification.domain.entities.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(): Flow<List<Notification>>
    fun getUnreadCount(): Flow<Int>
    suspend fun markAsRead(id: Int)
    suspend fun markAllAsRead()
    suspend fun deleteNotification(id: Int)
}
