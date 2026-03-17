package com.example.inventori2.features.product_delete.data.datasources.mapper

import com.example.inventori2.features.product_delete.data.datasources.models.ProductDeleteResponse
import com.example.inventori2.features.product_delete.domain.entities.ProductDeleteResult

fun ProductDeleteResponse.toDomain(): ProductDeleteResult {
    return ProductDeleteResult(
        success = true,
        message = this.message
    )
}