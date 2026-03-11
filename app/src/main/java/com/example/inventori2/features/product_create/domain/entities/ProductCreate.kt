package com.example.inventori2.features.product_create.domain.entities

import java.time.LocalDateTime

data class ProductCreate(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val fecha_vencimiento: String,
    val categoriaId: Int?,
    val usuarioId: Int,
    val createdAt: String? // Ahora es nullable para evitar el error
) {
    companion object {
        fun new(
            id: Int,
            nombre: String,
            cantidad: Int,
            fecha_vencimiento: String,
            categoriaId: Int?,
            usuarioId: Int
        ): ProductCreate {
            return ProductCreate(
                id = id,
                nombre = nombre,
                cantidad = cantidad,
                fecha_vencimiento = fecha_vencimiento,
                categoriaId = categoriaId,
                usuarioId = usuarioId,
                createdAt = LocalDateTime.now().toString()
            )
        }
    }
}