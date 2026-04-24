package com.example.inventori2.features.inventory.product_list.domain.usecases

import com.example.inventori2.features.inventory.product_list.domain.entities.Product
import com.example.inventori2.features.inventory.product_list.domain.repositories.ProductRepository

class GetAllProductUseCase (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return try {
            val products = repository.getAllProducts()
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}