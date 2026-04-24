package com.example.inventori2.core.notification

import android.util.Log
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.notification.data.datasources.dao.NotificationDao
import com.example.inventori2.features.notification.data.datasources.models.NotificationEntity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InventoriFirebaseMessagingService : FirebaseMessagingService() {

    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var tokenDataStore: TokenDataStore
    @Inject lateinit var notificationDao: NotificationDao

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        val title = message.notification?.title ?: data["title"] ?: "Notificación"
        val body = message.notification?.body ?: data["body"] ?: ""
        val type = data["type"] ?: "general"

        // 1. Mostrar la notificación visual (Hardware)
        notificationHelper.show(
            title = title,
            message = body,
            type = type,
            deepLinkData = data
        )

        // 2. Guardar en el historial local (Room)
        serviceScope.launch {
            val user = tokenDataStore.getUser().firstOrNull()
            notificationDao.insertNotification(
                NotificationEntity(
                    title = title,
                    message = body,
                    timestamp = System.currentTimeMillis(),
                    usuarioId = user?.id ?: 0
                )
            )
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM_TOKEN", "Nuevo token: $token")
        // Aquí enviarías el token a tu servidor para registrar este dispositivo
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
