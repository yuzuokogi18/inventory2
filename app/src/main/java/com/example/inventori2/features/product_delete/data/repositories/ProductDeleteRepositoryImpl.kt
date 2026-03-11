package com.example.inventori2.features.product_delete.data.repositories

import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.product_delete.domain.repositories.ProductDeleteRepository
import kotlinx.coroutines.flow.firstOrNull

class ProductDeleteRepositoryImpl(
    private val api: InventoriApi,
    private val tokenDataStore: TokenDataStore
) : ProductDeleteRepository {

    override suspend fun deleteProduct(productId: Int): Result<Unit> {
        return try {
            val token = tokenDataStore.getToken().firstOrNull() ?: ""
            api.deleteProduct("Bearer $token", productId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}