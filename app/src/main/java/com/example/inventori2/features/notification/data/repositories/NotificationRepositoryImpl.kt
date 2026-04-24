package com.example.inventori2.features.notification.data.repositories

import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.notification.data.datasources.dao.NotificationDao
import com.example.inventori2.features.notification.data.datasources.mapper.toDomain
import com.example.inventori2.features.notification.domain.entities.Notification
import com.example.inventori2.features.notification.domain.repositories.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val dao: NotificationDao,
    private val tokenDataStore: TokenDataStore
) : NotificationRepository {

    override fun getNotifications(): Flow<List<Notification>> {
        return tokenDataStore.getUser().flatMapLatest { user ->
            dao.getNotifications(user?.id ?: 0).map { entities ->
                entities.map { it.toDomain() }
            }
        }
    }

    override fun getUnreadCount(): Flow<Int> {
        return tokenDataStore.getUser().flatMapLatest { user ->
            dao.getUnreadCount(user?.id ?: 0)
        }
    }

    override suspend fun markAsRead(id: Int) = dao.markAsRead(id)

    override suspend fun markAllAsRead() {
        val user = tokenDataStore.getUser().firstOrNull()
        if (user != null) {
            dao.markAllAsRead(user.id)
        }
    }

    override suspend fun deleteNotification(id: Int) = dao.deleteById(id)
}
