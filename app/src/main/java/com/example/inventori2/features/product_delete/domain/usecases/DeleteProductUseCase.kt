package com.example.inventori2.features.product_delete.domain.usecases

import com.example.inventori2.features.product_delete.domain.repositories.ProductDeleteRepository

class DeleteProductUseCase(
    private val repository: ProductDeleteRepository
) {
    suspend operator fun invoke(productId: Int): Result<Unit> {
        return repository.deleteProduct(productId)
    }
}