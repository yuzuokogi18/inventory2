package com.example.inventori2.features.product_list.domain.repositories

import com.example.inventori2.features.product_list.domain.entities.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
}