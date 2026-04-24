package com.example.inventori2.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationChannels {

    const val INVENTORY    = "inventory_alerts"
    const val SHOPPING     = "shopping_reminders"
    const val GENERAL      = "general"

    fun createAll(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        listOf(
            NotificationChannel(
                INVENTORY,
                "Alertas de Inventario",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "Avisos de productos por caducar o stock bajo" },

            NotificationChannel(
                SHOPPING,
                "Lista de Compras",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "Recordatorios de artículos pendientes por comprar" },

            NotificationChannel(
                GENERAL,
                "General",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Notificaciones generales de la aplicación" }
        ).forEach { manager.createNotificationChannel(it) }
    }
}
