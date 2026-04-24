package com.example.inventori2.features.inventory.product_list.domain.entities

data class Product(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val fechaVencimiento: String,
    val categoriaId: Int?,
    val usuarioId: Int,
    val createdAt: String?,
    val imagenUri: String? = null
)
