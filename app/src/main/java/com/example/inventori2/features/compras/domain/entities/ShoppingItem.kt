package com.example.inventori2.features.compras.domain.entities

data class ShoppingItem(
    val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val cantidad: String,
    val isCompleted: Boolean = false,
    val isSuggestion: Boolean = false
)
