package com.example.inventori2.features.product_detail.data.datasources.mapper


import com.example.inventori2.features.product_detail.data.datasources.models.ProductDetailDTO
import com.example.inventori2.features.product_detail.domain.entities.ProductDetail

fun ProductDetailDTO.toDomain(): ProductDetail {
    return ProductDetail(
        id = this.id,
        nombre = this.nombre,
        cantidad = this.cantidad, // Corregido: antes era stock
        fechaVencimiento = this.fechaVencimiento, // Corregido: antes era fechaCaducidad
        categoriaId = this.categoriaId,
        usuarioId = this.usuarioId // Corregido: antes era stock
    )
}