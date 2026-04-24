package com.example.inventori2.features.inventory.product_edit.data.repositories

import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.database.entities.ProductEntity
import com.example.inventori2.features.inventory.product_edit.domain.entities.ProductEdit
import com.example.inventori2.features.inventory.product_edit.domain.repositories.ProductEditRepository

import javax.inject.Inject

class ProductEditRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductEditRepository {

    override suspend fun updateProduct(product: ProductEdit): Result<Unit> {
        return try {
            val currentProduct = productDao.getProductById(product.id)
                ?: return Result.failure(Exception("Producto no encontrado"))

            val updatedEntity = ProductEntity(
                id = product.id,
                nombre = product.nombre,
                cantidad = product.cantidad,
                fechaVencimiento = product.fechaVencimiento,
                categoriaId = product.categoriaId,
                usuarioId = currentProduct.usuarioId,
                createdAt = currentProduct.createdAt
            )

            productDao.updateProduct(updatedEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}