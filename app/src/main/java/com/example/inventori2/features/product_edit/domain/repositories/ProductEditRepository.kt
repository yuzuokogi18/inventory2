package com.example.inventori2.features.product_edit.domain.repositories

import com.example.inventori2.features.product_edit.domain.entities.ProductEdit

interface ProductEditRepository {
    suspend fun updateProduct(product: ProductEdit): Result<Unit>
}