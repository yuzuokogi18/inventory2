package com.example.inventori2.features.notification.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.notification.NotificationHelper
import com.example.inventori2.features.notification.data.datasources.dao.NotificationDao
import com.example.inventori2.features.notification.data.datasources.models.NotificationEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class ExpiryWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val productDao: ProductDao,
    private val notificationDao: NotificationDao,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now = Calendar.getInstance().apply { 
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        
        // Margen de "Próximo a vencer" (3 días)
        val nearLimit = Calendar.getInstance().apply { 
            time = now
            add(Calendar.DAY_OF_YEAR, 3) 
        }.time

        // Obtenemos todos los productos del usuario (ajustar ID si es necesario)
        val products = productDao.getAllProducts(0).firstOrNull() ?: return Result.success()

        products.forEach { product ->
            val expiryDate = try { sdf.parse(product.fechaVencimiento ?: "") } catch (e: Exception) { null }
            
            if (expiryDate != null) {
                val (title, message, type) = when {
                    expiryDate.before(now) -> {
                        Triple(
                            "🚨 Producto Vencido",
                            "Tu producto '${product.nombre}' venció el ${product.fechaVencimiento}. Te recomendamos desecharlo.",
                            "inventory_alert"
                        )
                    }
                    expiryDate == now || (expiryDate.after(now) && expiryDate.before(nearLimit)) -> {
                        Triple(
                            "⚠️ Vence Pronto",
                            "Tu producto '${product.nombre}' está por caducar (${product.fechaVencimiento}). ¡Úsalo pronto!",
                            "inventory_alert"
                        )
                    }
                    else -> null
                } ?: return@forEach

                // 1. Mostrar notificación física (Hardware)
                notificationHelper.show(title, message, type)

                // 2. Guardar en la campanita (MaaS Sync)
                notificationDao.insertNotification(
                    NotificationEntity(
                        title = title,
                        message = message,
                        timestamp = System.currentTimeMillis(),
                        usuarioId = product.usuarioId
                    )
                )
            }
        }
        return Result.success()
    }
}
