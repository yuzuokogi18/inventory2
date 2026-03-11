package com.example.inventori2.features.product_create.domain.repositories

import com.example.inventori2.features.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.product_create.domain.entities.ProductCreate

interface CreateProductRepository {
    suspend fun createProduct(product: ProductActionRequest): ProductCreate
}