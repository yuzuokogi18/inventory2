package com.example.inventori2.features.product_detail.domain.repositories


import com.example.inventori2.features.product_detail.domain.entities.ProductDetail

interface ProductDetailRepository {
    suspend fun getProductById(id: Int): ProductDetail
}