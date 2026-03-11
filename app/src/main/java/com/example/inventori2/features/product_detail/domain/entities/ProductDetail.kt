package com.example.inventori2.features.product_detail.domain.entities

data class ProductDetail(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val fechaVencimiento: String? = null,
    val categoriaId: Int?,
    val usuarioId: Int
)