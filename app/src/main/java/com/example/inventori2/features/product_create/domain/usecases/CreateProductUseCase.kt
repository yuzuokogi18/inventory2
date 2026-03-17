package com.example.inventori2.features.product_create.domain.usecases

import com.example.inventori2.features.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.product_create.domain.entities.ProductCreate
import com.example.inventori2.features.product_create.domain.repositories.CreateProductRepository

class CreateProductUseCase(
    private val repository: CreateProductRepository
) {
    suspend operator fun invoke(product: ProductActionRequest): Result<ProductCreate> {
        return try {
            val response = repository.createProduct(product)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}