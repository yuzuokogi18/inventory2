package com.example.inventori2.features.inventory.product_create.presentation.screens
import com.example.inventori2.features.inventory.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.inventory.product_create.domain.entities.ProductCreate

data class CreateProductUIState(
    val isLoading: Boolean = false,
    val productRequest: ProductActionRequest? = null,
    val productResponse: ProductCreate? = null,
    val isSuccess: Boolean = false,
    val error: String? = null
)