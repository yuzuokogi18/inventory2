package com.example.inventori2.features.product_detail.presentation.screens

import com.example.inventori2.features.product_detail.domain.entities.ProductDetail

data class ProductDetailUIState(
    val isLoading: Boolean = false,
    val product: ProductDetail? = null,
    val error: String? = null
)