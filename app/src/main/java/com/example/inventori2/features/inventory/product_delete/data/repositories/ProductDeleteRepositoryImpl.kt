package com.example.inventori2.features.inventory.product_delete.data.repositories

import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.features.inventory.product_delete.domain.repositories.ProductDeleteRepository

import javax.inject.Inject

class ProductDeleteRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductDeleteRepository {

    override suspend fun deleteProduct(productId: Int): Result<Unit> {
        return try {
            productDao.deleteProductById(productId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}