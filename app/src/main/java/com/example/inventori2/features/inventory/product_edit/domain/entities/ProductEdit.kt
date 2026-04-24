package com.example.inventori2.features.inventory.product_edit.domain.entities

data class ProductEdit(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val fechaVencimiento: String,
    val categoriaId: Int?,
    val imagenUri: String? = null // NUEVO
)
