package com.example.inventori2.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.database.dao.UserDao
import com.example.inventori2.core.database.entities.ProductEntity
import com.example.inventori2.core.database.entities.UserEntity
import com.example.inventori2.features.compras.data.datasources.dao.ShoppingDao
import com.example.inventori2.features.compras.data.datasources.models.ShoppingEntity
import com.example.inventori2.features.notification.data.datasources.dao.NotificationDao
import com.example.inventori2.features.notification.data.datasources.models.NotificationEntity

@Database(
    entities = [
        ProductEntity::class, 
        UserEntity::class, 
        ShoppingEntity::class, 
        NotificationEntity::class
    ], 
    version = 5, 
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun shoppingDao(): ShoppingDao
    abstract fun notificationDao(): NotificationDao
}
