package com.example.inventori2.features.product_edit.data.repositories

import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.product_edit.data.datasources.models.ProductEditRequest
import com.example.inventori2.features.product_edit.domain.entities.ProductEdit
import com.example.inventori2.features.product_edit.domain.repositories.ProductEditRepository
import kotlinx.coroutines.flow.firstOrNull

class ProductEditRepositoryImpl(
    private val api: InventoriApi,
    private val tokenDataStore: TokenDataStore
) : ProductEditRepository {

    override suspend fun updateProduct(product: ProductEdit): Result<Unit> {
        return try {
            // Corregido: se usa getToken() en lugar de .token
            val token = tokenDataStore.getToken().firstOrNull() ?: ""
            val request = ProductEditRequest(
                nombre = product.nombre,
                cantidad = product.cantidad,
                fechaVencimiento = product.fechaVencimiento,
                categoriaId = product.categoriaId
            )
            api.updateProduct("Bearer $token", product.id, request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}