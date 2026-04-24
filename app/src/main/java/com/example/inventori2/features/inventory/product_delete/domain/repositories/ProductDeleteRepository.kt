package com.example.inventori2.features.inventory.product_delete.domain.repositories

interface ProductDeleteRepository {
    suspend fun deleteProduct(productId: Int): Result<Unit>
}