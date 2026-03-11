package com.example.inventori2.features.product_detail.data.datasources.models

import com.google.gson.annotations.SerializedName

data class ProductDetailDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("cantidad")
    val cantidad: Int, // Coincide con 'cantidad' en Go

    @SerializedName("fecha_vencimiento")
    val fechaVencimiento: String?, // Coincide con 'fecha_vencimiento' en Go

    @SerializedName("categoria_id")
    val categoriaId: Int?,

    @SerializedName("usuario_id")
    val usuarioId: Int
)