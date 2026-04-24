package com.example.inventori2.features.notification.data.datasources.mapper

import com.example.inventori2.features.notification.data.datasources.models.NotificationEntity
import com.example.inventori2.features.notification.domain.entities.Notification

fun NotificationEntity.toDomain(): Notification {
    return Notification(
        id = id,
        title = title,
        message = message,
        timestamp = timestamp,
        isRead = isRead
    )
}
