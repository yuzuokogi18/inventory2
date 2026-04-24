package com.example.inventori2.features.notification.domain.usecases

import com.example.inventori2.features.notification.domain.entities.Notification
import com.example.inventori2.features.notification.domain.repositories.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(): Flow<List<Notification>> = repository.getNotifications()
}
