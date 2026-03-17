package com.example.inventori2.features.product_list.data.datasources.mapper

import com.example.inventori2.features.product_list.data.datasources.models.ProductResponse
import com.example.inventori2.features.product_list.domain.entities.Product

fun ProductResponse.toDomain(): Product {
    return Product(
        id = this.id,
        nombre = this.nombre,
        cantidad = this.cantidad,
        fechaVencimiento = this.fechaVencimiento,
        categoriaId = this.categoriaId,
        usuarioId = this.usuarioId,
        createdAt = this.createdAt ?: ""
    )
}