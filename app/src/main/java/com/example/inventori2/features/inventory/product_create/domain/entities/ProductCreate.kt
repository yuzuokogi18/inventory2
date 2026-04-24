package com.example.inventori2.features.inventory.product_create.domain.entities

import java.time.LocalDateTime

data class ProductCreate(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val fecha_vencimiento: String,
    val categoriaId: Int?,
    val usuarioId: Int,
    val createdAt: String?,
    val imagenUri: String? = null
) {
    companion object {
        fun new(
            id: Int,
            nombre: String,
            cantidad: Int,
            fecha_vencimiento: String,
            categoriaId: Int?,
            usuarioId: Int,
            imagenUri: String? = null
        ): ProductCreate {
            return ProductCreate(
                id = id,
                nombre = nombre,
                cantidad = cantidad,
                fecha_vencimiento = fecha_vencimiento,
                categoriaId = categoriaId,
                usuarioId = usuarioId,
                createdAt = LocalDateTime.now().toString(),
                imagenUri = imagenUri
            )
        }
    }
}