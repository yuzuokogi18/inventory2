package com.example.inventori2.features.inventory.product_edit.domain.usecases

import com.example.inventori2.features.inventory.product_edit.domain.entities.ProductEdit
import com.example.inventori2.features.inventory.product_edit.domain.repositories.ProductEditRepository

class UpdateProductUseCase(
    private val repository: ProductEditRepository
) {
    suspend operator fun invoke(product: ProductEdit): Result<Unit> {
        return repository.updateProduct(product)
    }
}