package com.example.inventori2.features.inventory.product_list.data.datasources.models

data class ProductRequest(
    val nombre: String,
    val cantidad: Int,
    val fecha_vencimiento: String,
    val categoria_id: Int?
)