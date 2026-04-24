package com.example.inventori2.features.inventory.product_list.presentation.screens

import com.example.inventori2.features.inventory.product_list.domain.entities.Product

data class ProductUIState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)
