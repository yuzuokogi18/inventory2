package com.example.inventori2.features.inventory.product_detail.data.datasources.models

import com.google.gson.annotations.SerializedName

data class ProductDetailDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("fecha_vencimiento")
    val fechaVencimiento: String?,
    @SerializedName("categoria_id")
    val categoriaId: Int?,

    @SerializedName("usuario_id")
    val usuarioId: Int
)