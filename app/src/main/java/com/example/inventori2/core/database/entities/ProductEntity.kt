package com.example.inventori2.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val cantidad: Int,
    val fechaVencimiento: String,
    val categoriaId: Int? = null,
    val usuarioId: Int = 0,
    val createdAt: String? = null,
    val imagenUri: String? = null // Nuevo campo para la imagen del hardware
)