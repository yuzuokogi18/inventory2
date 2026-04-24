package com.example.inventori2.features.inventory.product_detail.domain.entities

data class ProductDetail(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val fechaVencimiento: String? = null,
    val categoriaId: Int?,
    val usuarioId: Int,
    val imagenUri: String? = null // NUEVO
)
