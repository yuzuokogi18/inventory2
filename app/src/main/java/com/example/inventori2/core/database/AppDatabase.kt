package com.example.inventori2.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.database.dao.UserDao
import com.example.inventori2.core.database.entities.ProductEntity
import com.example.inventori2.core.database.entities.UserEntity

// Subimos la versión a 2 para forzar la recreación si hubo errores de esquema previos
@Database(entities = [ProductEntity::class, UserEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
}
