package com.example.inventori2.features.product_edit.presentation.screens

data class ProductEditUIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)