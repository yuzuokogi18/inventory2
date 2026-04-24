package com.example.inventori2.features.inventory.product_detail.data.datasources.mapper


import com.example.inventori2.features.inventory.product_detail.data.datasources.models.ProductDetailDTO
import com.example.inventori2.features.inventory.product_detail.domain.entities.ProductDetail

fun ProductDetailDTO.toDomain(): ProductDetail {
    return ProductDetail(
        id = this.id,
        nombre = this.nombre,
        cantidad = this.cantidad,
        fechaVencimiento = this.fechaVencimiento,
        categoriaId = this.categoriaId,
        usuarioId = this.usuarioId
    )
}