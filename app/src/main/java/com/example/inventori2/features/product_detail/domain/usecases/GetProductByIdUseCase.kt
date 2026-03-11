package com.example.inventori2.features.product_detail.domain.usecases


import com.example.inventori2.features.product_detail.domain.entities.ProductDetail
import com.example.inventori2.features.product_detail.domain.repositories.ProductDetailRepository

class GetProductByIdUseCase(
    private val repository: ProductDetailRepository
) {
    suspend operator fun invoke(productId: Int): Result<ProductDetail> {
        return try {
            val product = repository.getProductById(productId)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}