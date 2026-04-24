package com.example.inventori2.features.compras.data.datasources.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val cantidad: String,
    val isCompleted: Boolean = false,
    val usuarioId: Int
)
