package com.example.inventori2.features.product_edit.data.datasources.mapper

import com.example.inventori2.features.product_edit.data.datasources.models.ProductEditRequest
import com.example.inventori2.features.product_edit.domain.entities.ProductEdit

fun ProductEdit.toRequest(): ProductEditRequest {
    return ProductEditRequest(
        nombre = this.nombre,
        cantidad = this.cantidad,
        fechaVencimiento = this.fechaVencimiento,
        categoriaId = this.categoriaId
    )
}