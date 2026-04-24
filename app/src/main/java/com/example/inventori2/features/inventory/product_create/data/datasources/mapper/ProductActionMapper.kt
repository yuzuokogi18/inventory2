package com.example.inventori2.features.inventory.product_create.data.datasources.mapper


import com.example.inventori2.features.inventory.product_create.data.datasources.models.ProductActionDTO
import com.example.inventori2.features.inventory.product_create.domain.entities.ProductCreate


fun ProductActionDTO.toDomain(): ProductCreate {
    return ProductCreate(
        id = this.id,
        nombre = this.nombre,
        cantidad = this.cantidad,
        fecha_vencimiento = this.fechaVencimiento,
        categoriaId = this.categoriaId,
        usuarioId = this.usuarioId,
        createdAt = this.createdAt ?: java.time.LocalDateTime.now().toString()
    )
}