package com.example.inventori2.features.notification.data.datasources.dao

import androidx.room.*
import com.example.inventori2.features.notification.data.datasources.models.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications WHERE usuarioId = :userId ORDER BY timestamp DESC")
    fun getNotifications(userId: Int): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Int)

    @Query("UPDATE notifications SET isRead = 1 WHERE usuarioId = :userId")
    suspend fun markAllAsRead(userId: Int)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM notifications WHERE usuarioId = :userId AND isRead = 0")
    fun getUnreadCount(userId: Int): Flow<Int>
}
