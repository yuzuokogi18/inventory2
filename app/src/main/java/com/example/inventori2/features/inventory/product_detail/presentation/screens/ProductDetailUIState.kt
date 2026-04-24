package com.example.inventori2.features.inventory.product_detail.presentation.screens

import com.example.inventori2.features.inventory.product_detail.domain.entities.ProductDetail

data class ProductDetailUIState(
    val isLoading: Boolean = false,
    val product: ProductDetail? = null,
    val error: String? = null
)
